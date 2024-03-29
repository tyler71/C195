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

UPDATE country
    SET country='test',
        lastUpdateBy='auser'
    WHERE countryId = 14;
UPDATE city
    SET city='new city',
        countryId=14,
        lastUpdateBy='auser'
    WHERE cityId = 4;
UPDATE address
    SET address='new address',
        address2='new second address',
        cityId='4',
        postalCode='23532',
        phone='23523523',
        lastUpdateBy='auser'
    WHERE addressId='4';
UPDATE customer
    SET customerName='acustomer',
        addressId=2,
        active='1',
        lastUpdateBy='auser'
    WHERE customerId='4';

SELECT * from customer;

SELECT userId, userName FROM user WHERE userName = 'test';
SELECT userId FROM user WHERE userName = 'test';
SELECT userName, password FROM user WHERE userId = '1';

# int consultantName, String name, String address, String address2, String cityName, String countryName,
# String postalCode, String phone
SELECT cu.lastUpdateBy, cu.customerName, a.address, a.address2, ci.city,
        c.country, a.postalCode, a.phone FROM customer cu
            INNER JOIN address a ON cu.addressId = a.addressId
            INNER JOIN city ci on a.cityId = ci.cityId
            INNER JOIN country c on c.countryId = ci.countryId
        WHERE cu.customerId = 5;

DELETE from customer WHERE customerId = 0;

SELECT appointmentId, customerId, userId, title, description, type, start, end, createdBy, lastUpdateBy
FROM appointment WHERE userId = 1;

INSERT INTO appointment(customerId, userId, title, description, location, contact,
                        type, url, start, end, createDate, createdBy, lastUpdateBy)
                        VALUES(1, 1, 'Hello', 'A description', 'here', 'me',
                               'School', 'null', NOW(), NOW(), NOW(), 'me', 'me');

UPDATE appointment
SET
    customerId = 1,
    userId = 1,
    title = 'title',
    description = 'desc',
    location = 'location',
    contact = 'contact',
    type = 'type',
    url = 'url',
    start = NOW(),
    end = now(),
    lastUpdateBy = 'me'
WHERE appointmentId = 1;



