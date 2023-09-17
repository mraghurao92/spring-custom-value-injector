# Custom Spring Boot @InjectValue Annotation

This project provides a custom implementation of the `@InjectValue` annotation in Spring Boot, allowing for enhanced configuration management and monitoring of properties. The `@InjectValue` annotation is designed to work similarly to the built-in `@Value` annotation but with additional features.

## Features

- **Custom Annotation**: Replace the standard `@Value` annotation with `@InjectValue` for enhanced functionality.

- **Monitoring**: A Spring Scheduler is included in the project that runs every hour to pull `@InjectValue` properties from all beans and logs their content. This monitoring feature helps keep track of property values, including expiration dates.

## Usage

To use the `@InjectValue` annotation in your Spring Boot project, follow these steps:

1. Import the `InjectValue` annotation into your project.

    ```java
    import com.mraghu.spring.customizer.annotation.InjectValue;
    ```

2. Replace usages of the standard `@Value` annotation with `@InjectValue` in your codebase.

    ```java
    // Replace this
    @Value("${my.property}")
    private String myProperty;

    // With this
    @InjectValue(name="OPEN_API_KEY", value = "${my.property}", expireDate="2020-12-31", description="Open API key reference from Azure Key Vault", usedBy={"ABC ApiCall", "XYZ ApiCall", "Also referred in Kubernetes Secrets"})
    private String myOpenApiKey;
    ```

3. Configure your Spring Boot application to enable the custom functionality provided by `@InjectValue`.

4. In your `application.properties` or `application.yml`, make sure to specify the `project.root.package` property with the root package of your application. This property is required for the proper functioning of the `@InjectValue` annotation.

    ```properties
    project.root.package=com.example.myapp
    ```

5. The Spring Scheduler will automatically run every hour to pull `@InjectValue` properties from all beans and log their content for monitoring purposes.

### `@InjectValue` Annotation Attributes

- `name`: (Optional) A custom name for the property, typically used for reference or documentation purposes.
- `value`: (Required) The Spring Boot property placeholder expression that defines the property's value.
- `expireDate`: (Optional) The expiration date of the property, if applicable.
- `usedBy`: (Optional) An array of strings indicating where and how the property is used within the application.
- `description`: (Optional) A brief description or documentation for the property.

Here's an example of how to use these attributes in the `@InjectValue` annotation:

```java
@InjectValue(
    name = "OPEN_API_KEY",
    value = "${my.property}",
    expireDate = "2020-12-31",
    description = "Open API key reference from Azure Key Vault",
    usedBy = {"ABC ApiCall", "XYZ ApiCall", "Also referred in Kubernetes Secrets"}
)
private String myOpenApiKey;
```
## Dependencies

This project relies on Spring Boot and its core functionality. Ensure that you have Spring Boot set up correctly in your project's dependencies.

## Configuration
The monitoring functionality, which logs @InjectValue properties, is enabled by default and runs on an hourly basis. You can adjust the scheduling frequency or other configuration settings as needed for your project.

## Troubleshooting

If you encounter any issues or have questions regarding the usage of @InjectValue, please refer to the FAQ or reach out to us for assistance.


## Contributing

We welcome contributions to this project. If you have ideas for improvements or discover issues, please feel free to contribute or report them.


Author:
[Raghavender Muppavaram]()
