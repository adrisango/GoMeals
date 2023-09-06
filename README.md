# Go Meals
### B2C Web Application for Tiffin Service Vendors

Go Meals is a project that provides an interactive platform for Tiffin Service Vendors to reach interested customers.
Our B2C Web App simplifies daily Tiffin needs with interactive meal chart, weekly voting for a meal feature, and easy order management for vendors. 

Our ultimate goal is to simplify your daily Tiffin needs!

## BUILD INSTRUCTIONS

- Update Ubuntu packages

`$ sudo apt-get update `

- Clone the project and move to the frontend or backend folders accordingly to build each application. 

### Front End

- Download Node Js version 18.15.0
- Download npm
- To install the frontend dependencies, on the directory GoMeals/frontend, run the commands:

`$ npm install --legacy-peer-deps`

`$ npm build`

- Start the React application manually to test it's running:

`$ npm start`

- Stop the application

`ctrl + c`

### Back End

- Download gradle
- Download Java 17 
- To install the backend dependencies, on the directory GoMeals/backend, run the commands:

`$ gradle build`

- Start the Spring Boot application manually to test it's running:

`$ gradle bootRun`

- Stop the application 

`ctrl + c`

## DEPLOYMENT INSTRUCTIONS

Every time a new commit is made in the project, a GitLab CI/CD pipeline will run the Build, Test, Code Quality and
Deployment stages using a runner that directly runs the stages in the virtual machine. 
The project gets deployed into a virtual machine that runs Ubuntu Linux version 20.04.1 LTS (Focal Fossa).

Both the front end and backend are using NGINX as an HTTP server.

### BACKEND DEPLOYMENT

- Update Ubuntu packages

`$ sudo apt-get update `

- Create a systemd service file to manage the backend application.

`$ sudo nano /etc/systemd/system/gomeals.service `

- Build the backend gomeals application with gradle and generate a java jar of the project and save the directory of
  the jar.

- Add this content to the file: 

```
  [Unit]
  Description=Spring Boot gomeals
  After=syslog.target
  After=network.target[Service]
  User=root
  Type=simple
  
  [Service]
  ExecStart=/usr/bin/java -jar /home/csci5308vm23/backendjar/gomeals-0.0.1-SNAPSHOT.jar
  Restart=always
  StandardOutput=syslog
  StandardError=syslog
  SyslogIdentifier=gomeals
  
  [Install]
  WantedBy=multi-user.target
```

- On the ExecStart configuration of the file, a path to the spring boot jar has to be added. 
    - Manual backend deployment: Modify the path */home/csci5308vm23/backendjar/gomeals-0.0.1-SNAPSHOT.jar* 
  and replace it with to the latest build of the backend jar. 
    - Automatic deployment with the CI/CD pipeline: The jar *gomeals-0.0.1-SNAPSHOT.jar* in the directory 
  */home/csci5308vm23/backendjar/* gets automatically updated with every successful execution of the pipeline.

- Save the file and reload the systemd daemon.

`$ sudo systemctl daemon-reload `

- Start the gomeals backend service and let it run every time the system boot:

```
  $ sudo systemctl start gomeals
  $ sudo systemctl enable gomeals
```

- Check the gomeals backend service status:

`$ sudo systemctl status gomeals`

- Install NGINX

` $ apt-get install nginx -y`

- Create a virtual host configuration file for the backend:

`$ sudo nano /etc/nginx/conf.d/gomeals.conf `

- Add this content to the file: 

```
  server {
          listen 80;
          listen [::]:80;
  
          server_name csci5308vm23.research.cs.dal.ca;
  
          location / {
               proxy_pass http://localhost:8080/;
               proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
               proxy_set_header X-Forwarded-Proto $scheme;
               proxy_set_header X-Forwarded-Port $server_port;
          }
  }
```

- Replace the server name with the name of the server that is running the application.
- Save the file and restart NGINX service to save the configuration changes. 

`$ systemctl restart nginx`

- The backend Spring Boot is now configured and running with NGINX, open a browser and access it with the URL: 

` http://csci5308vm23.research.cs.dal.ca:8080/ `

- To enable / disable the gomeals service to reflect any new changes made to the backend jar, run the following commands:

```
    $ sudo systemctl daemon-reload
    $ sudo systemctl restart gomeals
    $ sudo systemctl status gomeals
```
### FRONTEND DEPLOYMENT

- Update Ubuntu packages

`$ sudo apt-get update `

- Ensure that nodejs and npm are installed.

` $node --version && npm --version`

- Create a production build of the gomeals frontend React application with the command:

`$ npm run build`

- This command will compile the javascript package.json build script in the build directory of the
frontend application. If the execution was successful it will show this message:

```
  The project was built assuming it is hosted at /.
  You can control this with the homepage field in your package.json.
  The build folder is ready to be deployed.
  You may serve it with a static server:
    npm install -g serve
    serve -s build
  Find out more about deployment here:
    https://cra.link/deployment
```

- To configure NGINX to deploy the frontend gomeals react application, create a directory to store the build 
files:

`$ mkdir /var/www/html/react`

- Copy the contents from the build directory to the new created directory:

`$ cp -r /home/csci5308vm23/group23/GoMeals/frontend/build/* /var/www/html/react/`

- Change the build directory accordingly depending on where the frontend project was installed and built.

- Create NGINX virtual host configuration file to host the frontend gomeals application. 

`$ sudo nano /etc/nginx/conf.d/react.conf`

- Add this content to the file: 

```
  server {
           listen 3000;
           root /var/www/html/react/;
           index index.html index.html;
  
           server_name csci5308vm23@csci5308vm23.research.cs.dal.ca;
  
  }

```

- Save the contents of the file.

- Restart the NGINX service. 

`$ systemctl restart nginx` 

- Every time a new change is made to the frontend react application, the application build files need to be updated and
the NGINX service needs to be restarted. 

### CI/CD PIPELINE RUNNER 

- Download and install the runner on the desired server that is going to host the application:

```
  # Download the binary for your system
  $ sudo curl -L --output /usr/local/bin/gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-linux-amd64
  
  # Give it permission to execute
  $ sudo chmod +x /usr/local/bin/gitlab-runner
  
  # Create a GitLab Runner user
  $ sudo useradd --comment 'GitLab Runner' --create-home gitlab-runner --shell /bin/bash
  
  # Install and run as a service
  $ sudo gitlab-runner install --user=gitlab-runner --working-directory=/home/gitlab-runner
  $ sudo gitlab-runner start
  
```

- Register the runner: 

` $ sudo gitlab-runner register --url https://git.cs.dal.ca/ --registration-token $REGISTRATION_TOKEN `

- The runner used on the CI/CD pipeline is tagged as r2.
- Allow all commands made from the CI/CD pipelines run without requiring password sudo.

` $ sudo usermod -a -G sudo gitlab-runner`

- Start sudo editor

` $ sudo visudo `


- Add the following line at the end of the runner configuration file :

` gitlab-runner ALL=(ALL) NOPASSWD: ALL `

- Modify the package json to disable warnings as errors by adding on the build scrip the following command:

```
  "scripts": {
      "build": "CI=false && react-scripts build",
    }
```

- The project automatically deploys the applications on every commit. Both frontend and backend need to be
built (backend jar file and react build files) before applying the changes.















