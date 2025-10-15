#!/usr/bin/env python3
import json
import sys
import time
import requests

def main():
    input_json = sys.stdin.read()
    data = json.loads(input_json)
    running_instance_id = data.get('running_instance_id')
    files = data.get('files', [])

    # Hardcoded parameters
    repo = 'your-org/your-repo'
    branch_base = 'feature/automation'
    commit_message = 'Automated commit of files for instance {}.'.format(running_instance_id)
    user_email = 'automation-bot@yourdomain.com'
    token = 'ghp_xxxYOURTOKENHERExxx'

    timestamp = time.strftime('%Y%m%d-%H%M%S')
    branch_name = f"{branch_base}-{timestamp}"

    file_dict = {}
    for f in files:
        file_dict[f['file_name']] = f['Code']

    # Prepare GitHub API request
    api_url = f"https://api.github.com/repos/{repo}/git/refs/heads/main"
    headers = {
        'Authorization': f'token {token}',
        'Accept': 'application/vnd.github.v3+json'
    }
    # Get main branch SHA
    resp = requests.get(api_url, headers=headers)
    if resp.status_code != 200:
        print(json.dumps({"running_instance_id": running_instance_id, "error": resp.text}))
        return
    main_sha = resp.json()['object']['sha']

    # Create new branch
    create_branch_url = f"https://api.github.com/repos/{repo}/git/refs"
    branch_data = {
        "ref": f"refs/heads/{branch_name}",
        "sha": main_sha
    }
    resp = requests.post(create_branch_url, headers=headers, json=branch_data)
    if resp.status_code != 201:
        print(json.dumps({"running_instance_id": running_instance_id, "error": resp.text}))
        return

    # Commit files
    for file_path, content in file_dict.items():
        commit_url = f"https://api.github.com/repos/{repo}/contents/{file_path}"
        commit_data = {
            "message": commit_message,
            "content": content.encode('utf-8').decode('utf-8'),
            "branch": branch_name,
            "committer": {
                "name": "Automation Bot",
                "email": user_email
            }
        }
        resp = requests.put(commit_url, headers=headers, json=commit_data)
        if resp.status_code not in [201, 200]:
            print(json.dumps({"running_instance_id": running_instance_id, "error": resp.text}))
            return

    branch_url = f"https://github.com/{repo}/tree/{branch_name}"
    print(json.dumps({"running_instance_id": running_instance_id, "branch_url": branch_url}))

if __name__ == "__main__":
    main()
