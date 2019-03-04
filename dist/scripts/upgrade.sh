#!/bin/bash

echo "Downloading new dist from S3... "
rm /home/ec2-user/castlerocktrek-1.0-SNAPSHOT.zip

aws s3 cp s3://castlerocktrek/application.prod.conf /home/ec2-user
aws s3 cp s3://castlerocktrek/castlerocktrek-1.0-SNAPSHOT.zip /home/ec2-user
sudo -H -u ec2-user unzip -o /home/ec2-user/castlerocktrek-1.0-SNAPSHOT.zip -d /home/ec2-user

# systemctl
echo "Copying service file..."
sudo cp /home/ec2-user/castlerocktrek/dist/services/castlerocktrek.service /etc/systemd/system/castlerocktrek.service
sudo chmod 644 /etc/systemd/system/castlerocktrek.service

echo "Restarting service... "
sudo systemctl enable castlerocktrek.service
sudo systemctl daemon-reload
sudo systemctl restart castlerocktrek

