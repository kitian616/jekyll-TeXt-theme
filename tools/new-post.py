import argparse
from datetime import datetime
# import os

def create_post_header(post_key, license, author, tags, date):
    # 创建一个字典来保存头部信息
    header = {
        'layout': 'article',
        'title': f'【Topic】Main Title',
        'permalink': f'/article/:title.html',
        'key': post_key,
        'tags': tags.split(","),
        'author': author,
        'show_author_profile': 'true',
        'license': license,
    }

    # 将字典转换为 YAML 格式的字符串
    header_yaml = '---\n'
    for key, value in header.items():
        if isinstance(value, list):
            header_yaml += f'{key}:\n'
            for item in value:
                header_yaml += f'  - {item}\n'
        else:
            header_yaml += f'{key}: {value}\n'
    header_yaml += '---\n\n'

    return header_yaml

def create_new_post(post_key, license, author, tags, date, template_path, new_post_path):
    # 生成头部
    post_header = create_post_header(post_key, license, author, tags, date)

    # 读取模板文件的其余部分
    with open(template_path, 'r') as file:
        content = file.read().split('---', 2)[-1]

    # 保存为新的文件
    with open(new_post_path, 'w') as file:
        file.write(post_header + content)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Create a new Jekyll post.')
    parser.add_argument('post_key', type=str, help='The key of the post')
    parser.add_argument('--license', type=str, default='WTFPL', help='License of the post')
    parser.add_argument('--author', type=str, default='Yu Xiaoyuan', help='Author of the post')
    parser.add_argument('--tags', type=str, default='tag', help='Comma-separated tags')
    parser.add_argument('--date', type=str, default=datetime.now().strftime('%Y-%m-%d'), help='Date of the post')

    args = parser.parse_args()

    template_path = '_posts/1970-01-01-template.md'
    new_post_path = f'_posts/{args.date}-{args.post_key}.md'

    create_new_post(args.post_key, args.license, args.author, args.tags, args.date, template_path, new_post_path)
