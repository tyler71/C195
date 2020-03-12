SELECT userName, password from user WHERE userId = 1;

SELECT userName, password from user;

# Adding a new Customer
INSERT INTO country(country, createDate, createdBy, lastUpdateBy)
VALUES('USA', NOW(), 'Tyler', 'Tyler');
INSERT INTO city(city, countryId, createDate, createdBy, lastUpdateBy)
                VALUES('Seattle', 1, NOW(), 'Tyler', 'Tyler');
INSERT INTO address(address, address2, cityId, postalCode, phone,
                     createDate, createdBy, lastUpdateBy)
                     VALUES ('123 Main Street', 'NA', 1, 'NA', '555-555-5555',
                             NOW(), 1, 1);
INSERT INTO customer(customerName, addressId, active, createDate,
                     createdBy, lastUpdateBy)
                    VALUES('Bob', 1, 1, NOW(), 'Tyler', 'Tyler');
