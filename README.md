# Parcel Tracking Chat Bot
A very simple and straightforward chat-bot that utilizes openAI in providing humanly response. It only supports providing updates of Parcel status. Data of parcel will be retrieved from the existing values in database.

## Database Setup

### Loading Initial Database

1. **Prerequisites:**
    - PostgreSQL installed and running locally.
    - Access to `psql` command-line utility.

2. **Steps:**

   ### a. Navigate to Project Directory:
      ```
      cd /path/to/your/project
      ```

   ### b. Restore Database from Dump File:
      ```
      psql -U your_username -d your_database_name -f sqldata/intial_db.dump
      ```

   ### c. Verification:
   After restoration, connect to your database and verify:
      ```
      psql -U your_username -d your_database_name
      \dt  -- List all tables
      SELECT * FROM your_table_name;  -- Sample query to check data
      ```

3. **Notes:**
    - Ensure `intial_db.dump` is in the `sqldata` folder relative to your project.
    - Adjust paths and database names as per your project setup.
    - Update the `application.properties` to reflect the correct api from openAI
    - model used in config/property is `gpt-3.5-turbo`
    - Don't forget to run `mvn clean install` 
