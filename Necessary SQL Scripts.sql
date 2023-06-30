CREATE TABLE `employee` (
                            `SSN` int(9) PRIMARY KEY,
                            `DOB` varchar(255),
                            `FName` text(200),
                            `Minit` text(200),
                            `LName` text(200)
);

CREATE TABLE `department` (
                              `deptnum` int PRIMARY KEY AUTO_INCREMENT,
                              `deptname` text,
                              `numemp` int
);

CREATE TABLE `department_locations` (
                                        `deptnum` int PRIMARY KEY,
                                        `location` text
);

CREATE TABLE `department_employees` (
                                        `deptnum` int PRIMARY KEY,
                                        `employee` int
);

CREATE TABLE `dependent` (
                             `id` int PRIMARY KEY AUTO_INCREMENT,
                             `employee` int,
                             `Name` text,
                             `DOB` varchar(50)
);

CREATE TABLE `project` (
                           `projname` varchar(50),
                           `projnum` int PRIMARY KEY,
                           `projectdesc` varchar(255)
);

CREATE TABLE `project_departments` (
                                       `projname` varchar(50),
                                       `projnum` int PRIMARY KEY,
                                       `deptnum` int
);

CREATE TABLE `manager` (
                           `employee` int PRIMARY KEY,
                           `department` int,
                           `OfficeNum` int,
                           `StartDate` varchar(255)
);

CREATE TABLE `pay` (
                       `employee` int PRIMARY KEY,
                       `SalaryPay` int,
                       `HourlyPay` double
);