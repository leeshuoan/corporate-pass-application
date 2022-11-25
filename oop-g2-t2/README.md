# IS442 OOP G2 T2 

## Local development
### 🔧 Setting up
1️⃣ Clone our repository from GitHub to your `desired_folder_name` for both frontend and backend respectively
```bash
# frontend
git clone https://github.com/emocado/oop-g2-t2-frontend.git desired_folder_name
# backend
git clone https://github.com/umardanialjr/oop-g2-t2.git desired_folder_name
```
<br></br>


2️⃣ Edit the database password `spring.datasource.password` according to your system settings
```
~/project-2022-23t1-project-2022-23t1-g2-t8/server/src/main/resources/application.properties
```
<br></br>

3️⃣ Open mamp or wamp and start the servers and run the following command in phpmyadmin / MySQL Workbench
```bash 
CREATE DATABASE `oop`;
```

<br></br>


#### 🏃 Running the application 
4️⃣ Open a new terminal and perform the commands below to install the required dependencies for the frontend and run the frontend app
```bash
cd ~/oop-g2-t2-frontend/
npm install 
npm run dev
```
<br></br>

5️⃣ Open another new terminal and and perform the commands to install Java and spring
```bash
cd ~/oop-g2-t2/
mvn spring-boot:run
```
<br></br>


## 6️⃣ Trying out our application

#### ✅ You can access the local webpage [here](http://localhost:5173/).
You can log in as admins with the following users:
| role         | username                | password |
|--------------|-------------------------|----------|
| admin        | admin@gmail.com         | admin    |
| gop          | gop@gmail.com           | gop      |
| employee     | employee@gmail.com      | employee |

<br></br>

### API Documentation
Can be found in the `api documentation` folder

#### Optional - Installation guides
[Install MAMP](https://www.mamp.info/en/downloads/) for mac users
[install WAMP](https://www.wampserver.com/en/) for windows users

