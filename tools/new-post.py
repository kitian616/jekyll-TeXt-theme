import argparse
from datetime import datetime
import os

def create_new_post(post_key, license, author, tags, date):
    template_path = '_posts/1970-01-01-template.md'
    new_post_path = f'_posts/{date}-{post_key}.md'
    title = f"【some topic】{post_key}"

    # 读取模板文件
    with open(template_path, 'r') as file:
        content = file.read()

    formatted_tags = "\n  - ".join(tags.split(","))
    
    # 替换字段
    content = content.replace('key: template-key', f'key: {post_key}')
    content = content.replace('author: Yu Xiaoyuan', f'author: {author}')
    content = content.replace('license: WTFPL', f'license: {license}')
    content = content.replace('tags:\n  - template\n  - blog\n  - Liquid\n  - jekyll', f'tags:\n  - {formatted_tags}')
    content = content.replace('【some topic】main title', title)

    # 保存为新的文件
    with open(new_post_path, 'w') as file:
        file.write(content)

    print(f"Post created at {new_post_path}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Create a new Jekyll post.')
    parser.add_argument('post_key', type=str, help='The key of the post')
    parser.add_argument('--license', type=str, default='WTFPL', help='License of the post')
    parser.add_argument('--author', type=str, default='Yu Xiaoyuan', help='Author of the post')
    parser.add_argument('--tags', type=str, default='tag', help='Comma-separated tags')
    parser.add_argument('--date', type=str, default=datetime.now().strftime('%Y-%m-%d'), help='Date of the post')

    args = parser.parse_args()

    create_new_post(args.post_key, args.license, args.author, args.tags, args.date)
