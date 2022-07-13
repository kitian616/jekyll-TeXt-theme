---
title: GPU and Conda environment
sidebar:
    nav: os-setup-ko
aside:
    toc: true
key: 20220701
tags: SetUp
---

### 1. [선택사항] 기기에 연결된 GPU 확인해보기

`sudo lshw -C display`

<p>
    <img src="/assets/images/post/2022-07-01-os-setup/gpuenv_0.png"> 
    <p align="center">
    <em> Check GPU Hardware Connection</em>
    </p>
</p>
    
### 2. Python/Compiler/Build tool/cuDNN/CUDA 버전 확인하기

모든 패키지를 설치전에 항상 종속되는 장비의 버전을 먼저 확인해보고 들어가야 함을 주의하자. 
각 라이브러리(Tensorflow or PyTorch version) 에 맞는 Python/Compiler/Build tool/cuDNN/CUDA 버전을 하단 링크를 통해 확인하고 다음으로 넘어가자.

Tensorflow: [https://www.tensorflow.org/install/source](https://www.tensorflow.org/install/source)

PyTorch: [https://pytorch.org/get-started/locally/](https://pytorch.org/get-started/locally/)

PyTorch는 CUDA 11.3, 10.2를 메인으로 해, tensorflow와 호환하려면 아래 디스커션을 참고하면 되겠다.

[https://discuss.pytorch.org/t/want-to-install-pytorch-for-custom-cuda-version-cuda-11-2/141159](https://discuss.pytorch.org/t/want-to-install-pytorch-for-custom-cuda-version-cuda-11-2/141159)

예시. 

| Version | Python version | Compiler | Build tools | cuDNN | CUDA |
| --- | --- | --- | --- | --- | --- |
| TensorFlow-2.7.0 | 3.7-3.9 | GCC 7.3.1 | Bazel 3.7.2 | 8.1 | 11.2 |

### 3. NVIDIA driver 설치

- Reference. [https://sseongju1.tistory.com/10](https://sseongju1.tistory.com/10)
- Nvidia driver: [https://www.nvidia.com/download/index.aspx?lang=en-us](https://www.nvidia.com/download/index.aspx?lang=en-us)
- Tensorflow/Pytorch 버전과 맞는 Cuda에 해당하는 드라이버

#### 1) 장치들의 드라이버 찾기
    
`sudo ubuntu-drivers devices`
    
#### 2) 장치에 맞는 드라이버 자동으로 설치
    
`sudo ubuntu-drivers auto install`
    
#### 3) 위에서 해당 장치의 드라이버를 찾은 후에 수동 설치
    
`sudo apt install nvidia-drivers-470`

*필자의 경우 3090을 사용하는데 driver-470이 나왔습니다. 사용하는 그래픽 카드에 따라 driver-[version]을 설치해주면 됩니다.

#### 4) NVIDIA 그래픽 드라이버 확인하기
    
`nvidia-smi`
<p>
    <img src="/assets/images/post/2022-07-01-os-setup/gpuenv_1.png"> 
    <p align="center">
    <em> 그래픽 카드 드라이버 설치 확인하기</em>
    </p>
</p>    
    
#### 4-1) `nvidia-drivers-xxx` 가 설치가 불가한 경우 

이 경우 운영체제 패키지를 업데이트 후, openssh-server, net-tools, build-essential, manages-dev를 설치후 gcc를 확인하고 나니 드라이버가 문제는 해결됐다. 아마 gcc 컴파일러가 있는지 확인 후 드라이버를 설치하면 될 듯 싶었다.(나의 경우엔 업데이트부터 했지만!)

- 운영체제 패키지 업데이트
    
    `sudo apt udate && sudo apt upgrade -y`
    
- 패키지 업데이트 및 필요 패키지 설치
    
    `sudo apt install openssh-server`
    
    `sudo apt install net-tools`
    
- 소스코드 빌드 및 컴파일 시 필요한 패키지 설치
    
    `sudo apt install build-essential`
    
    `sudo apt-get install manages-dev`
    
- 정상 설치 확인
    
    `gcc --version`
        
#### 4-2) 이후에 문제가 생긴다면(Black-Screen이라고 불린다는…) 

이 [링크](https://sseongju1.tistory.com/10)를 참조하기 바란다. 

### 4 CUDA, cuDNN 설치하기

- Reference. [https://developnote.tistory.com/20](https://developnote.tistory.com/20)
- 이전 쿠다 버전을 삭제하는 경우: `sudo apt-get --purge -y remove ‘cuda*’`

#### 1) CUDA    
[CUDA 공식 사이트](https://developer.nvidia.com/cuda-toolkit)에 들어가서 Download를 클릭하고 Resource란에 • Archive of Previous CUDA Releases로 들어가면 [이 경로](https://developer.nvidia.com/cuda-toolkit-archive)가 나온다. 여기서 원하는 버전을 설치하는 가이드가 나온다.

필자의 경우 Linux → x86_64(64bit) → Ubuntu → 20.04 → deb(local) 로 설정했다.
<p>
    <img src="/assets/images/post/2022-07-01-os-setup/gpuenv_2.png"> 
    <p align="center">
    <em> CUDA Library Option</em>
    </p>
</p>
    
설치 확인은 `nvidia-smi` 혹은 `nvcc -V` 를 통해서 볼 수 있다. 설치한 버전과 표시된 버전이 다르면 `~/.bashrc` 내에 PATH를 수정하면 된다.

<p>
    <img src="/assets/images/post/2022-07-01-os-setup/gpuenv_3.png"> 
    <p align="center">
    <em> Bash Configuration for adding Cuda library</em>
    </p>
</p>    

bashrc 업데이트시에는 `source ~/.bashrc` !
    
#### 2) cuDNN
cuDNN: [https://developer.nvidia.com/rdp/cudnn-archive](https://developer.nvidia.com/rdp/cudnn-archive)

위 링크에서 다운로드한 파일들을 CUDA 드라이브 안에 복사해줘야 한다.

필자의 경우 Ubuntu20.04 LTS 환경아래에서 `/usr/local/cuda-11.2` 에 복사해줬다.
    
- 윈도우의 경우 **경로 추가**는 항상 필수!

### 5. Anaconda 설치하기
    
Multi user와 Single user의 차이는 생각 보다 간단하게, “권한을 나누는 방식”을 알면 이해하기 쉽다. 로컬시스템에서 그룹에게 conda가 설치된 폴더에 권한을 줘서 read와 write를 하게 해주면 된다. 그리고 Anaconda 대신 Miniconda를 설치한 이유는 하단에 적은 듯이 Miniconda에 meta package를 제외시키고 공용으로 사용하는 부분의 무게를 덜기 위해서 이다. 

#### 1) `cd /tmp` 
#### 2) Download the installation file in the terminal

`curl -O https://repo.anaconda.com/miniconda/Miniconda3-py38_4.12.0-Linux-x86_64.sh Miniconda3-py38_4.12.0-Linux-x86_64.sh` 

#### 3) Install Miniconda

`bash Miniconda3-py38_4.12.0-Linux-x86_64.sh`
    
conda의 path에 따라 sudo를 사용해야할 수도 있다. sudo를 사용하는 Conda initialization에서 path가 root/.bashrc 에서 설정되니 no를 추천한다.

#### 4) After agreeing the license, you need to set the path of conda 

<p>
    <img src="/assets/images/post/2022-07-01-os-setup/gpuenv_4.png"> 
    <p align="center">
    <em> Installation Path Option when installing conda</em>
    </p>
</p>    
        
이 경우 원하는 경로를 사용해준다. 필자는 `/opt/miniconda3` 로 설정하였다.<sup>[1](#footnote_1)</sup>
    
#### 5) Selecting conda initialization

<p>
    <img src="/assets/images/post/2022-07-01-os-setup/gpuenv_5.png"> 
    <p align="center">
    <em> Selection of initialization while installing the conda</em>
    </p>
</p>    

필자의 경우 `no` 를 선택하고 이후 6번의 과정에서 bash configuration을 수정했다. (conda init을 사용하는 사람들도 있는데 그 방법도 비슷한 설정일 것 같다.)<sup>[1](#footnote_1)</sup>

#### 6) bash configuration
    
본인의 계정(user)에서 conda 명령어를 사용하고 싶은 경우에 반드시 bash configuration이 필요하다. 이는 Ubuntu환경에서 /home/[user_name]/.bashrc 에서 수정할 수 있다.

```bash
# cuda
export PATH="/usr/local/cuda-11.2/bin:$PATH"
export LD_LIBRARY_PATH="/usr/local/cuda-11.2/lib64:$LD_LIBRARY_PATH"

# >>> conda initialize >>>
# !! Contents within this block are managed by 'conda init' !!
__conda_setup="$('/opt/anaconda3/bin/conda' 'shell.bash' 'hook' 2> /dev/null)"
if [ $? -eq 0 ]; then
    eval "$__conda_setup"
else
    if [ -f "/opt/anaconda3/etc/profile.d/conda.sh" ]; then
        . "/opt/anaconda3/etc/profile.d/conda.sh"
    else
        export PATH="/opt/anaconda3/bin:$PATH"
    fi
fi
unset __conda_setup
# <<< conda initialize <<<
export PATH=/opt/anaconda3/bin:/opt/anaconda3/condabin:$PATH
```

반드시 수정한 후에는 `source [bash configuration file]` 을 해야 설정이 된다. (ex. `source /home/[user_name]/.bashrc`)

이제 conda를 이용해서 환경을 구성하면 된다!
    
#### Reference

- <a name="footnote_1"><sup>1</sup>Conda Installation</a> : [https://www.digitalocean.com/community/tutorials/how-to-install-anaconda-on-ubuntu-18-04-quickstart](https://www.digitalocean.com/community/tutorials/how-to-install-anaconda-on-ubuntu-18-04-quickstart)

- [Single-user Anaconda installation](https://docs.anaconda.com/anaconda/install/linux/)
        
- [Anaconda installation in Terminal on Ubuntu](https://www.digitalocean.com/community/tutorials/how-to-install-anaconda-on-ubuntu-18-04-quickstart)
        
- [Multi-user Anaconda installation](https://docs.anaconda.com/anaconda/install/multi-user/)
        
- [MiniConda installation](https://docs.conda.io/en/latest/miniconda.html#linux-installers)
            
#### Q. 왜 Tensorflow blog에서는 miniconda를 쓰라고 할까?
    
Reference. [https://stackoverflow.com/questions/45421163/anaconda-vs-miniconda](https://stackoverflow.com/questions/45421163/anaconda-vs-miniconda)

```bash
💡 `conda` is both a command line tool, and a python package.
Miniconda installer = Python + `conda`
Anaconda installer = Python + `conda` + *meta package* `anaconda`
meta Python pkg `anaconda` = about 160 Python pkgs for daily use in data science
Anaconda installer = Miniconda installer + `conda install anaconda`
```

### 5. Tensorflow 설치하기
    
아래 내용은 Tensorflow는 원하는 버전에 맞춰서 [Tensorflow 공식 링크](https://www.tensorflow.org/install/pip)의 설명대로 설치해주면 된다. 

링크와 똑같은 내용을 왜 굳이 적었냐고? 

```
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$CONDA_PREFIX/lib/ 
이 설정이 어디서 적용되는지 궁금했기 때문이다. 이는 /home/[user_name]/.bashrc 에서 
export LD_LIBRARY_PATH="/usr/local/cuda-11.2/lib64:$LD_LIBRARY_PATH" 
로 4번에서 이미 설정해준 부분이다. 아마 CUDA 라이브러리를 명시해준 것이겠지?
```

- conda 환경에서 `conda create --name [env_name] python=[version]` 로 환경을 만든다.
- `conda activate [env_name]`
- `conda install -c conda-forge cudatoolkit=11.2 cudnn=8.1.0`
- `export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$CONDA_PREFIX/lib/`
- `python3 -m pip install tensorflow==[version]`
- build 시 [https://www.tensorflow.org/install/source](https://www.tensorflow.org/install/source) 참조


### 6. TensorFlow 설치 확인

- `python -c "import tensorflow as tf; print(\"Num GPUs Available: \", len(tf.config.list_physical_devices('GPU')))"`
- Verify CPU setup: `python3 -c "import tensorflow as tf; print(tf.reduce_sum(tf.random.normal([1000, 1000])))"`
- [TODO] Q. What is this alert?
    
    2022-07-01 15:58:41.337424: I tensorflow/stream_executor/cuda/cuda_gpu_executor.cc:937] successful NUMA node read from SysFS had negative value (-1), but there must be at least one NUMA node, so returning NUMA node zero
    
### [참고 사항] .bashrc에 cuda/conda path 추가하기

```bash
# cuda
export PATH="/usr/local/cuda-11.2/bin:$PATH"
export LD_LIBRARY_PATH="/usr/local/cuda-11.2/lib64:$LD_LIBRARY_PATH"

# >>> conda initialize >>>
# !! Contents within this block are managed by 'conda init' !!
__conda_setup="$('/opt/anaconda3/bin/conda' 'shell.bash' 'hook' 2> /dev/null)"
if [ $? -eq 0 ]; then
    eval "$__conda_setup"
else
    if [ -f "/opt/anaconda3/etc/profile.d/conda.sh" ]; then
        . "/opt/anaconda3/etc/profile.d/conda.sh"
    else
        export PATH="/opt/anaconda3/bin:$PATH"
    fi
fi
unset __conda_setup
# <<< conda initialize <<<
export PATH=/opt/anaconda3/bin:/opt/anaconda3/condabin:$PATH
```