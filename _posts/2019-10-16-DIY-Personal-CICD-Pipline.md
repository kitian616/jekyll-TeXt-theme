---
title: "DIY Personal CI/CD Pipline"
toc: true
mermaid: true
categories: [Productivity]
tags:
  - azure
  - devops
---

Using Docker, Azure DevOps and Azure Container Instances we are going to setup a simple pipe-line that will build our image from code to Docker image, tag and push that image to a private Azure Container Registry and deploy the latest version of said container to a Azure Container Instance. I use this personally for a few small projects like my personal dashboard. I edit, run and test the code on my local machine using Docker and then commit the code my DevOps GiT repository.

This looks a little like this:

![Overview](https://blog.benstein.nl/assets/images/3d-DrawingPipeline.png)

## Ingredients

Some pre requirements. This guide assumes you are using Azure DevOps and DevOps GiT. You also need to link your DevOps environment to your Azure Subscription. Details for DevOps and DevOps GiT are here:[DevOps Setup](https://docs.microsoft.com/en-us/azure/devops/user-guide/sign-up-invite-teammates?view=azure-devops) 

And more on adding Azure to your DevOps environment is here: 

So before we get started, check if you have the following: [Add Azure to DevOps Pipeline](https://docs.microsoft.com/en-us/azure/devops/pipelines/library/connect-to-azure?view=azure-devops)

- Active Azure environment;
- Azure DevOps;
- Azure DevOps Service set up to connect to your Azure environment;
- Az CLI installed on you system (see: [Installing Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest)) . You can also use the Azure Web CLI from your Azure Portal.

Some notes, I'm using `<Placeholder>` to indicate places where you need to provide code. I'm also using Azure CLI in BASH so I'll be using `\` to do linebreaks. Replace them with backticks ``` ` ``` if you're using PowerShell.

## Setting up Azure
In your Azure environment we are going to setup a Private Azure Container Registry and a Resource Group to deploy your Container instance, get started by login in:
```bash
    az login
```
After login in create your resource group

```bash
    az group create \
    --location "West Europe" \
    --name RG-ContainerInstance \
    --tags CiCDTutorial
```

Now we are going to create the Private Container Repository

```bash
    az acr create \
    --name PrivateRepository \
    --resource-group RG-ContainerInstance \
    --sku Basic \
    --admin-enabled true \
    --tags CiCDTutorial
```

Now (from the Azure Portal) go to `Dashboard > Container registries > PrivateRepository - Access keys` :

![Azure Portal](https://blog.benstein.nl/assets/images/Untitled-37464db4-5794-4f9f-93b6-798822be18f1.png)

Note down the following:
- Login server
- Username
- Password

You'll need this in your Build pipeline.

Now we're done on the Azure side. Time to switch over to Azure DevOps

## Setting up DevOps Pipeline
In Azure DevOps navigate to your project and go to `Pipelines > Builds`. We are going to setup a pipeline that is triggered by a commit to the Master Branch. Select `New` and then `New Build Pipeline` . 

Select your source (in this case I'm using Azure Repos)

![Pipeline source](https://blog.benstein.nl/assets/images/Untitled-e4a56081-daa8-4c38-ab78-56a39211c583.png)

After selecting your repo chose a `Starter pipeline`

![Template select](https://blog.benstein.nl/assets/images/Untitled-bbe832bf-1a5a-45ad-b798-1a495477fb21.png)

After this you are presented with the basic pipeline YAML. Replace the YAML with the following code

```yaml
    # Starter pipeline
    # Start with a minimal pipeline that you can customize to build and deploy your code.
    # Add steps that build, run tests, deploy, and more:
    # https://aka.ms/yaml
    
    trigger:
    - master
    
    pool:
      vmImage: 'ubuntu-latest'
    
    steps:
    - script: echo Building and Pusing image
      displayName: 'Building and Pusing image'
      
    - task: Docker@2
      inputs:
        containerRegistry: 'Azure Docker Repository'
        repository: 'YourRepository'
        command: 'buildAndPush'
        Dockerfile: '**/Dockerfile'
        tags: 'latest'
    
    - task: AzureCLI@2
      inputs:
        azureSubscription: '<YourSubscriptionName> (<YourSubscriptionID>)'
        scriptType: 'bash'
        scriptLocation: 'inlineScript'
        inlineScript: |
          az container delete \
          --name YourContainerInstance \
          --resource-group RG-ContainerInstance \
          --yes
    
    - task: AzureCLI@2
      inputs:
        azureSubscription: '<YourSubscriptionName> (<YourSubscriptionID>)'
        scriptType: 'bash'
        scriptLocation: 'inlineScript'
        inlineScript: |
          az container create \
          --image <Login server>/YourRepository \
          --registry-login-server <Login server> \
          --registry-username <Username> \
          --registry-password <Password> \
          --resource-group RG-ContainerInstance \
          --name YourContainerInstance \
          --cpu 0.5 \
          --memory 1 \
          --ports 3030 \
          --environment-variables GEMS=uptimerobot \
          --restart-policy always \
          --dns-name-label YourDNSLabel \
          --restart-policy always \
    	  --tags CiCDTutorial 
```

In this case I'm deploying a Docker image and giving it `0.5 CPU` , `1 GB RAM`, Parsing in some ENV variables and exposing `port 3030`. You can customize this to your liking. You can find more details on creating Azure Container Instances on the [Docs page](https://docs.microsoft.com/en-us/cli/azure/container?view=azure-cli-latest#az-container-create)

This pipline will do the following steps:

1. Build a Docker image acoarding to the Dockerfile in the root of your GiT Repo;
2. Tag that image as `latest` and push it to you private Repository
3. In case that your current Container Instance is running the first Azure CLI command will delete it;
4. Deploy a new Container Instance named `YourContainerInstance` with a few extra variables.

After the deployment you can check if your image is running using `az container list --output table`
