# George Mukha | Personal Website and CV

This repository contains the source code for a personal website and resume platform built with the Ktor framework and Kotlin.
The project features integrated self-hosted Umami analytics, secure environment variable management, and a pull-based continuous deployment system.

---

## Technical Stack

* **Backend**: Kotlin 2.2.20 with Ktor 3.3.2.
* **Frontend**: HTML DSL and Tailwind CSS.
* **Analytics**: Umami (PostgreSQL database).
* **Infrastructure**: Docker and Docker Compose.
* **Proxy Server**: Nginx.
* **Deployment**: Bash script automation and Cron.

---

## Environment Configuration

A `.env` file must be created in the project root to manage sensitive data.
Use the provided `.env.example` as a template.

### Required Variables

| Variable           | Description                 | Origin                         |
|:-------------------|:----------------------------|:-------------------------------|
| `DB_NAME`          | Database name for analytics | Defined in `.env`              |
| `DB_USER`          | Database username           | Defined in `.env`              |
| `DB_PASSWORD`      | Database password           | User-defined (secure string)   |
| `UMAMI_APP_SECRET` | Session encryption secret   | Randomly generated string      |
| `UMAMI_WEBSITE_ID` | Unique website identifier   | Obtained from Umami dashboard  |
| `UMAMI_URL`        | Public URL for analytics    | `https://stats.yourdomain.com` |

---

## Server Deployment

### 1. Prerequisites
Install Docker, Docker Compose, and Nginx on the target VPS:
`sudo apt update && sudo apt install docker.io docker-compose nginx -y`

### 2. Project Installation
Clone the repository to the `/opt/cv-website` directory and configure the environment:
```bash
git clone [https://github.com/themukha/cv-website.git](https://github.com/themukha/cv-website.git) /opt/cv-website
cd /opt/cv-website
cp .env.example .env
# Edit .env with your specific credentials
nano .env
```

### 3. Continuous Deployment Automation
The `deploy.sh` script automates the process of fetching updates from the repository and rebuilding the environment.
Configure a Cron job to execute this script every five minutes to ensure the production environment remains synchronized with the `master` branch:

```bash
chmod +x deploy.sh
crontab -e
```

Append the following line to the crontab file:
```shell
*/5 * * * * /opt/cv-website/deploy.sh >> /var/log/cv-deploy.log 2>&1
```

### 4. Nginx and SSL Configuration
Nginx is utilized as a reverse proxy to manage incoming traffic and facilitate SSL termination through Cloudflare. This configuration allows both the primary website and the analytics dashboard to operate on standard ports (80/443).

#### Configuration Procedure
Create a new configuration file at `/etc/nginx/sites-available/cv-site`:

```nginx
server {
    listen 80;
    server_name themukha.tech; # Primary Domain

    location / {
        proxy_pass [http://127.0.0.1:8080](http://127.0.0.1:8080);
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}

server {
    listen 80;
    server_name stats.themukha.tech; # Analytics Subdomain

    location / {
        proxy_pass [http://127.0.0.1:3000](http://127.0.0.1:3000);
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

Enable the configuration and restart the service:
```shell
sudo ln -s /etc/nginx/sites-available/cv-site /etc/nginx/sites-enabled/
sudo rm /etc/nginx/sites-enabled/default
sudo nginx -t
sudo systemctl restart nginx
```

### 5. DNS Configuration (e.g. Cloudflare)
The following A records must be established within the Cloudflare dashboard to route traffic to the server.
Ensure the "Proxied" status is enabled to utilize Cloudflare's SSL and security features.

| Type  | Name    | Content         | Proxy Status     |
|:------|:--------|:----------------|:-----------------|
| **A** | `@`     | 185.194.141.172 | Proxied (Orange) |
| **A** | `stats` | 185.194.141.172 | Proxied (Orange) |

---

### 6. Umami Analytics Setup
1. Access the analytics interface via `https://stats.themukha.tech`.
2. **Initial Credentials**: Use `admin` as the username and `umami` as the default password.
3. **Security**: Change the administrative password immediately in the *Settings > Profile* section.
4. **Website Integration**:
    * Navigate to *Settings > Websites > Add website*.
    * Register your domain (e.g., `themukha.tech`).
    * Copy the generated `Website ID`.
    * Update the `UMAMI_WEBSITE_ID` variable in the server's `.env` file and restart the containers.

---

### 7. Local Development
For local testing and building without Docker, utilize the following Gradle tasks:

| Task                           | Description                                                       |
|:-------------------------------|:------------------------------------------------------------------|
| `./gradlew run`                | Execute the Ktor server locally                                   |
| `./gradlew buildFatJar`        | Assemble an executable JAR with all dependencies included         |
| `./gradlew test`               | Execute the project's test suite                                  |
| `docker-compose up -d --build` | Deploy the full stack (App + Analytics + DB) using Docker Compose |
