import os
import re

def strip_comments(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    original_content = content

    if file_path.endswith('.java'):
        # 1. Remove block comments /* ... */
        # We use a regex that avoids matching inside strings, but for simplicity, 
        # a standard non-greedy block comment regex is usually fine for student projects.
        content = re.sub(r'/\*.*?\*/', '', content, flags=re.DOTALL)
        
        # 2. Remove single line comments // ...
        # Match // but NOT when it's preceded by a colon (like http://)
        # We'll use a regex that matches // and everything after it on the same line,
        # provided it doesn't have : right before it.
        # Actually, let's just do a line-by-line processing for // to be safe and preserve indent
        lines = content.split('\n')
        new_lines = []
        for line in lines:
            # Find // that is not preceded by :
            # Using regex: (?<!:)//.*
            new_line = re.sub(r'(?<!:)//.*', '', line)
            # if line became entirely whitespace and wasn't before, maybe we can keep it empty or strip
            if new_line.strip() == '' and line.strip() != '':
                continue # Skip the line entirely if it was just a comment
            new_lines.append(new_line.rstrip())
        content = '\n'.join(new_lines)

    elif file_path.endswith('.jsp'):
        # Remove JSP comments <%-- ... --%>
        content = re.sub(r'<%--.*?--%>', '', content, flags=re.DOTALL)
        
        # Remove HTML comments <!-- ... -->
        # But be careful with conditional comments if any, usually fine to strip all
        content = re.sub(r'<!--.*?-->', '', content, flags=re.DOTALL)

    if content != original_content:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Cleaned: {file_path}")

def main():
    src_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'src')
    for root, dirs, files in os.walk(src_dir):
        for file in files:
            if file.endswith('.java') or file.endswith('.jsp'):
                strip_comments(os.path.join(root, file))

if __name__ == '__main__':
    main()
