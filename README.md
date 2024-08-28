This repository houses the Java EE version of the PTO Backend API. It handles tasks in the PTO system. The API uses layered architecture and includes various Jakarta EE specs such as Servlet, JSON Binding (JSON-B), and JNDI. These help it to carry out CRUD operations well. The backend API configured to the database through a connection pool set up in the Jakarta EE <Context> and <web-app> configs.


>>Layered Architecture.

  The API has a layered structure. This keeps different jobs separate and makes upkeep easier. Each layer does its own job, like business logic, data access, and presentation.

>>CRUD Operations.

The API can Create, Read, Update, and Delete data. This lets it manage all the PTO system's data.

>>Connection Pooling with JNDI.

Database connectivity is handled well using a connection pool set up in the Jakarta EE <Context> configuration. This setup helps the system work better by letting it deal with many connections at the same time.

>>Web.xml configuration.

The web.xml file, commonly referred to as the deployment descriptor, is a required configuration file for the PTO Backend API. It describes basic application settings, such as servlets, filters, listeners, and objects, including connection pool environment variables. This centralized framework simplifies the management of API services in servlet containers such as Apache Tomcat.

>>Validation Mechanisms.

The API has strong checks in place to keep data clean in all its tasks. It looks for needed conditions before handling requests, which cuts down on mistakes and keeps data consistent.

>>Logging and Monitoring.

The API has a full logging system that uses SLF4J. It records key actions and errors. This helps with keeping an eye on things and fixing problems during both making and using the product.

>>Cross-Origin Resource Sharing (CORS).

There's a CORS filter to handle requests from different sources. This makes sure the front-end and the API can talk to each other safely.
