# NullGuard

NullGuard is a lightweight static analysis tool focused on detecting potential `NullPointerException`s (NPE) and runtime crash risks in Java projects.

## Features
- Analyzes Java source code for potential NPEs.
- Includes pre-built rules (e.g., unthecked parameter dereferences, `Optional.get()` without `isPresent()`, and autounboxing risks).
- Provides a fast, multi-threaded project scanner.
- Generates JSON and HTML reports.
- Supports execution via CLI or Maven Plugin.

## Quick Start
You can run NullGuard via the command line or directly integrate it into your Maven projects.

### CLI Usage
```bash
# Build the CLI jar:
mvn clean package -pl nullguard-cli -am

# Run the CLI:
java -jar nullguard-cli/target/nullguard-cli.jar /path/to/your/project
```

### Maven Usage
Include the plugin in your `pom.xml`:
```xml
<plugin>
    <groupId>io.nullguard</groupId>
    <artifactId>nullguard-maven-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <executions>
        <execution>
            <goals>
                <goal>analyze</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
Run `mvn nullguard:analyze` to scan your project.

## Example Output
The reporting engine will output files to `target/nullguard-report/` in both `.html` and `.json` formats summarizing the risk profile of each package and class scanned.

## Contributing
See [CONTRIBUTING.md](CONTRIBUTING.md) for a guide on how to add new rules or improve the engine.
