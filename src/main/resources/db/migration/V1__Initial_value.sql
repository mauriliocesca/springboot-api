CREATE TABLE addresses (
  id INT AUTO_INCREMENT NOT NULL,
   country VARCHAR(255) NOT NULL,
   city VARCHAR(255) NOT NULL,
   zip_code VARCHAR(255) NOT NULL,
   street VARCHAR(255) NOT NULL,
   state VARCHAR(255) NULL,
   CONSTRAINT pk_addresses PRIMARY KEY (id)
);

CREATE TABLE contracts (
  id INT AUTO_INCREMENT NOT NULL,
   code VARCHAR(20) NOT NULL,
   final_date datetime NOT NULL,
   initial_date datetime NOT NULL,
   user_id INT NOT NULL,
   property_id INT NOT NULL,
   laboratory_id INT NOT NULL,
   note LONGTEXT NULL,
   CONSTRAINT pk_contracts PRIMARY KEY (id)
);

CREATE TABLE laboratories (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(100) NOT NULL,
   CONSTRAINT pk_laboratories PRIMARY KEY (id)
);

CREATE TABLE properties (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(100) NOT NULL,
   pin INT NOT NULL,
   CONSTRAINT pk_properties PRIMARY KEY (id)
);

CREATE TABLE users (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(100) NOT NULL,
   email VARCHAR(255) NOT NULL,
   birth_date date NOT NULL,
   gender VARCHAR(10) NOT NULL,
   created_at datetime NULL,
   updated_at datetime NULL,
   address_id INT NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE contracts ADD CONSTRAINT uc_contracts_code UNIQUE (code);

ALTER TABLE properties ADD CONSTRAINT uc_properties_pin UNIQUE (pin);

ALTER TABLE users ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE contracts ADD CONSTRAINT FK_CONTRACTS_ON_LABORATORY FOREIGN KEY (laboratory_id) REFERENCES laboratories (id);

ALTER TABLE contracts ADD CONSTRAINT FK_CONTRACTS_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES properties (id);

ALTER TABLE contracts ADD CONSTRAINT FK_CONTRACTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES addresses (id);