import Portfolio.edit_frontmatter as ed
import glob
import pandas as pd

## RUN:
if __name__ == "__main__":
    lista_posts = glob.glob("*.md")
    lista_hd = []
    for filename in lista_posts:
        post,_,_ = ed.load_frontmatter(filename, _parsing=False)
        dicty = {"name":filename, "metadata":post.metadata, "content": post.content,}
        lista_hd.append(dicty)

    Posts = pd.DataFrame(lista_hd)

    order_by_column = lambda col: Posts.sort_values(col, ascending=False).set_index(col)
    Posts = order_by_column(col="name")
    Posts.to_csv("Posts.csv")

    post = Posts.iloc[0]

## FUNCTIONS:
def cambiar_metadatos(df = Posts, cambio:dict = {}):
    for m in df["metadata"]:
        for k,v in cambio.items():
            m[k] = v

def make_new_one(filename:str, metadata=post.metadata):
    """
    make_new_post(filename:str, post_header=post.metadata):

    Make a new post , assigning path filename and a post as template (header_post), which will pass it's YAML frontmatter.
    """
    new_post = ed.frontmatter.Post(content="", **metadata)
    with open(filename, "w") as f:
        f.write(ed.frontmatter.dumps(new_post))


