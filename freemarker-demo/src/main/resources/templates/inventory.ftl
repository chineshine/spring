[all]
${ssh.ip} ansible_ssh_user=${ssh.user}

[all:vars]
action=${os_auth.action}
auth_url=${os_auth.os_auth_url}
username=${os_auth.os_auth_userName}
password=${os_auth.os_auth_Password}
project_name=${os_auth.os_auth_projectName}
domain_name=${os_auth.os_auth_domainName}
server=${os_auth.serverId}