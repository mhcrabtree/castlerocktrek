#!/bin/bash
yum update -y
yum install -y httpd.x86_64
yum install -y polkit

#yum install -y mod_ssl
#yum install -y git
yum install -y java-1.8.0-openjdk
#curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
#yum install -y sbt

systemctl start httpd.service
systemctl enable httpd.service
echo "Hello World from $(hostname -f)" > /var/www/html/index.html

#su - ec2-user -c "git clone https://github.com/mhcrabtree/castlerocktrek.git /home/ec2-user/castlerocktrek"
#cd /home/ec2-user
#su - ec2-user -c "sbt dist"

systemctl restart httpd.service