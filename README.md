# JetNinja

JetNinja is a Java-based automation tool for signing up for a free trial account of IntelliJ IDEA Ultimate, a popular integrated development environment (IDE) developed by JetBrains. The tool can also generate a temporary email address to use for the registration process to avoid the need for a real email address.

## Features

- Automated sign-up process for IntelliJ IDEA Ultimate free trial account
- Temporary email address generation for sign-up process

## Usage

1. Clone the JetNinja repository to your local machine.
2. Install Java 11 or higher if not already installed.
3. Build the project using Maven by running the command `mvn package` in the root directory of the project.
4. Run the JetNinja tool using the command `java -jar target/JetNinja-1.0-SNAPSHOT.jar`.

## Configuration

The JetNinja tool can be configured using the following environment variables:

- `JETNINJA_URL`: The URL of the IntelliJ IDEA Ultimate sign-up page. Default: `https://www.jetbrains.com/idea/download/`
- `JETNINJA_API_URL`: The URL of the temporary email address API. Default: `https://api.tempmail.lol/auth/`
- `JETNINJA_EMAIL_DOMAIN`: The email domain to use for the temporary email address. Default: `1secmail.com`
- `JETNINJA_WAIT_TIME`: The time in seconds to wait between HTTP requests. Default: `5`

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.
