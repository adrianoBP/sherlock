-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 07, 2018 at 11:17 PM
-- Server version: 10.1.23-MariaDB-9+deb9u1
-- PHP Version: 7.0.30-0+deb9u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sherlock_util`
--

-- --------------------------------------------------------

--
-- Table structure for table `currency`
--

CREATE TABLE `currency` (
  `id` int(11) NOT NULL,
  `code` varchar(3) NOT NULL,
  `name` varchar(255) NOT NULL,
  `symbol` varchar(10) NOT NULL,
  `value` decimal(20,10) NOT NULL,
  `country` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `currency`
--

INSERT INTO `currency` (`id`, `code`, `name`, `symbol`, `value`, `country`) VALUES
(1, 'AFN', 'Afghan afghani', '؋', '85.1895520000', 'Afghanistan'),
(2, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Anguilla'),
(3, 'AUD', 'Australian dollar', '$', '1.5986750000', 'Australia'),
(4, 'BRL', 'Brazilian real', 'R$', '4.2228360000', 'Brazil'),
(5, 'KHR', 'Cambodian riel', '៛', '4620.6425650000', 'Cambodia'),
(6, 'CUP', 'Cuban peso', '$', '30.0852660000', 'Cuba'),
(7, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Dominica'),
(8, 'EUR', 'European euro', '€', '1.0000000000', 'Finland'),
(9, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Grenada'),
(10, 'INR', 'Indian rupee', '₹', '83.5859160000', 'India'),
(11, 'ILS', 'Israeli new sheqel', '₪', '4.2146390000', 'Israel'),
(12, 'KZT', 'Kazakhstani tenge', 'лв', '418.5940080000', 'Kazakhstan'),
(13, 'EUR', 'European euro', '€', '1.0000000000', 'Luxembourg'),
(14, 'MYR', 'Malaysian ringgit', 'RM', '4.7494980000', 'Malaysia'),
(15, 'MUR', 'Mauritian rupee', '₨', '39.3566380000', 'Mauritius'),
(16, 'MNT', 'Mongolian tugrik', '₮', '2912.2390880000', 'Mongolia'),
(17, 'NOK', 'Norwegian krone', 'kr', '9.5476990000', 'Norway'),
(18, 'EUR', 'European euro', '€', '1.0000000000', 'Portugal'),
(19, 'RSD', 'Serbian dinar', 'Дин.', '118.1978330000', 'Serbia'),
(20, 'EUR', 'European euro', '€', '1.0000000000', 'Slovenia'),
(21, 'EUR', 'European euro', '€', '1.0000000000', 'Spain'),
(22, 'SEK', 'Swedish krona', 'kr', '10.4036020000', 'Sweden'),
(23, 'TZS', 'Tanzanian shilling', 'TSh', '2599.8215900000', 'Tanzania'),
(24, 'UAH', 'Ukrainian hryvnia', '₴', '31.9857850000', 'Ukraine'),
(25, 'UZS', 'Uzbekistani som', 'лв', '9354.8143480000', 'Uzbekistan'),
(26, 'YER', 'Yemeni rial', '﷼', '284.1827980000', 'Yemen'),
(27, 'ARS', 'Argentine peso', '$', '41.7543730000', 'Argentina'),
(28, 'AZN', 'Azerbaijani manat', 'ман', '1.9328400000', 'Azerbaijan'),
(29, 'BYN', 'New Belarusian ruble', 'p.', '2.4034040000', 'Belarus'),
(30, 'BOB', 'Bolivian boliviano', '$b', '7.8468620000', 'Bolivia'),
(31, 'BGN', 'Bulgarian lev', 'лв', '1.9557720000', 'Bulgaria'),
(32, 'CAD', 'Canadian dollar', '$', '1.4916050000', 'Canada'),
(33, 'CNY', 'Chinese renminbi', '¥', '7.9099260000', 'China'),
(34, 'CRC', 'Costa Rican colon', '₡', '677.1676570000', 'Costa Rica'),
(35, 'CZK', 'Czech koruna', 'Kč', '25.8958210000', 'Czech Republic'),
(36, 'USD', 'U.S. Dollar', '$', '1.1352930000', 'Ecuador'),
(37, 'EUR', 'European euro', '€', '1.0000000000', 'Estonia'),
(38, 'HKD', 'Hong Kong dollar', '$', '8.9064310000', 'Hong Kong'),
(39, 'IRR', 'Iranian rial', '﷼', '47801.5140950000', 'Iran, Islamic Republic of'),
(40, 'JMD', 'Jamaican dollar', 'J$', '152.7651240000', 'Jamaica'),
(41, 'AUD', 'Australian dollar', '$', '1.5986750000', 'Kiribati'),
(42, 'LAK', 'Lao kip', '₭', '9701.0793490000', 'Laos'),
(43, 'MKD', 'Macedonian denar', 'ден', '61.4556570000', 'Macedonia (Former Yug. Rep.)'),
(44, 'USD', 'U.S. Dollar', '$', '1.1352930000', 'Micronesia'),
(45, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Montserrat'),
(46, 'AUD', 'Australian dollar', '$', '1.5986750000', 'Nauru'),
(47, 'NIO', 'Nicaraguan cordoba', 'C$', '36.4999350000', 'Nicaragua'),
(48, 'PKR', 'Pakistani rupee', '₨', '151.1529030000', 'Pakistan'),
(49, 'PEN', 'Peruvian nuevo sol', 'S/.', '3.8156630000', 'Peru'),
(50, 'QAR', 'Qatari riyal', '﷼', '4.1336520000', 'Qatar'),
(51, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Saint Kitts and Nevis'),
(52, 'SOS', 'Somali shilling', 'S', '658.4702140000', 'Somalia'),
(53, 'SYP', 'Syrian pound', '£', '584.6758200000', 'Syria'),
(54, 'GBP', 'British pound', '£', '0.8931350000', 'United Kingdom'),
(55, 'EUR', 'European euro', '€', '1.0000000000', 'Andorra'),
(56, 'BSD', 'Bahamian dollar', '$', '1.1238830000', 'Bahamas'),
(57, 'EUR', 'European euro', '€', '1.0000000000', 'Belgium'),
(58, 'BAM', 'Bosnia and Herzegovina konvertibilna marka', 'KM', '1.8097740000', 'Bosnia-Herzegovina'),
(59, 'KYD', 'Cayman Islands dollar', '$', '0.9455910000', 'Cayman Islands'),
(60, 'COP', 'Colombian peso', '$', '3631.6321800000', 'Colombia'),
(61, 'DKK', 'Danish krone', 'kr', '7.4618270000', 'Denmark'),
(62, 'EGP', 'Egyptian pound', '£', '20.3319660000', 'Egypt'),
(63, 'GIP', 'Gibraltar pound', '£', '0.8855230000', 'Gibraltar'),
(64, 'HUF', 'Hungarian forint', 'Ft', '324.6142960000', 'Hungary'),
(65, 'JPY', 'Japanese yen', '¥', '128.0792450000', 'Japan'),
(66, 'KPW', 'North Korean won', '₩', '1021.8456030000', 'Korea North'),
(67, 'LVL', 'Latvian lats', 'Ls', '0.6867270000', 'Latvia'),
(68, 'CHF', 'Swiss Franc', 'Fr.', '1.1405190000', 'Liechtenstein'),
(69, 'EUR', 'European Euro', '€', '1.0000000000', 'Malta'),
(70, 'NPR', 'Nepalese rupee', '₨', '133.7713650000', 'Nepal'),
(71, 'USD', 'U.S. Dollar', '$', '1.1352930000', 'Palau'),
(72, 'PHP', 'Philippine peso', '₱', '60.7364710000', 'Philippines'),
(73, 'RON', 'Romanian leu', 'lei', '4.6650310000', 'Romania'),
(74, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Saint Lucia'),
(75, 'SAR', 'Saudi riyal', '﷼', '4.2582560000', 'Saudi Arabia'),
(76, 'SGD', 'Singapore dollar', '$', '1.5718130000', 'Singapore'),
(77, 'ZAR', 'South African rand', 'R', '16.6478010000', 'South Africa'),
(78, 'SRD', 'Surinamese dollar', '$', '8.4670530000', 'Suriname'),
(79, 'TWD', 'New Taiwan dollar', 'NT$', '35.1356190000', 'Taiwan'),
(80, 'AUD', 'Australian dollar', '$', '1.5986750000', 'Tuvalu'),
(81, 'USD', 'United States dollar', '$', '1.1352930000', 'United States of America'),
(82, 'VND', 'Vietnamese dong', '₫', '26503.4163000000', 'Vietnam'),
(83, 'ALL', 'Albanian lek', 'Lek', '124.8711600000', 'Albania'),
(84, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Antigua and Barbuda'),
(85, 'EUR', 'European euro', '€', '1.0000000000', 'Austria'),
(86, 'BBD', 'Barbadian dollar', '$', '2.2714380000', 'Barbados'),
(87, 'BND', 'Brunei dollar', '$', '1.6013330000', 'Brunei'),
(88, 'CLP', 'Chilean peso', '$', '787.6617110000', 'Chile'),
(89, 'EUR', 'European euro', '€', '1.0000000000', 'Cyprus'),
(90, 'DOP', 'Dominican peso', 'RD$', '57.0193640000', 'Dominican Republic'),
(91, 'EUR', 'European euro', '€', '1.0000000000', 'France'),
(92, 'EUR', 'European euro', '€', '1.0000000000', 'Germany'),
(93, 'GTQ', 'Guatemalan quetzal', 'Q', '8.7879650000', 'Guatemala'),
(94, 'HNL', 'Honduran lempira', 'L', '27.3588870000', 'Honduras'),
(95, 'IDR', 'Indonesian rupiah', 'Rp', '17275.2434910000', 'Indonesia'),
(96, 'EUR', 'European euro', '€', '1.0000000000', 'Italy'),
(97, 'KES', 'Kenyan shilling', 'KSh', '115.6892150000', 'Kenya'),
(98, 'KGS', 'Kyrgyzstani som', 'лв', '79.1116450000', 'Kyrgyzstan'),
(99, 'LRD', 'Liberian dollar', '$', '177.8986660000', 'Liberia'),
(100, 'MXN', 'Mexican peso', '$', '22.8004080000', 'Mexico'),
(101, 'EUR', 'European Euro', '€', '1.0000000000', 'Montenegro'),
(102, 'NAD', 'Namibian dollar', '$', '16.5068920000', 'Namibia'),
(103, 'NZD', 'New Zealand dollar', '$', '1.7320010000', 'New Zealand'),
(104, 'OMR', 'Omani rial', '﷼', '0.4369690000', 'Oman'),
(105, 'PYG', 'Paraguayan guarani', 'Gs', '6809.7162830000', 'Paraguay'),
(106, 'USD', 'U.S. Dollar', '$', '1.1352930000', 'Puerto Rico'),
(107, 'SHP', 'Saint Helena pound', '£', '1.4996110000', 'Saint Helena'),
(108, 'EUR', 'European euro', '€', '1.0000000000', 'San Marino'),
(109, 'SCR', 'Seychellois rupee', '₨', '15.4284650000', 'Seychelles'),
(110, 'SBD', 'Solomon Islands dollar', '$', '9.3904060000', 'Solomon Islands'),
(111, 'LKR', 'Sri Lankan rupee', '₨', '195.5201590000', 'Sri Lanka'),
(112, 'CHF', 'Swiss franc', 'Fr.', '1.1405190000', 'Switzerland'),
(113, 'THB', 'Thai baht', '฿', '37.7713640000', 'Thailand'),
(114, 'AWG', 'Aruban florin', 'ƒ', '2.0435270000', 'Aruba'),
(115, 'BZD', 'Belize dollar', 'BZ$', '2.2806910000', 'Belize'),
(116, 'BWP', 'Botswana pula', 'P', '12.0818000000', 'Botswana'),
(117, 'HRK', 'Croatian kuna', 'kn', '7.4304870000', 'Croatia'),
(118, 'USD', 'U.S. Dollar', '$', '1.1352930000', 'El Salvador'),
(119, 'FJD', 'Fijian dollar', '$', '2.4436620000', 'Fiji'),
(120, 'EUR', 'European euro', '€', '1.0000000000', 'Greece'),
(121, 'GYD', 'Guyanese dollar', '$', '236.9694790000', 'Guyana'),
(122, 'ISK', 'Icelandic króna', 'kr', '137.7000460000', 'Iceland'),
(123, 'EUR', 'European euro', '€', '1.0000000000', 'Ireland'),
(124, 'KRW', 'South Korean won', '₩', '1293.0533950000', 'Korea South'),
(125, 'LBP', 'Lebanese lira', '£', '1714.3488720000', 'Lebanon'),
(126, 'EUR', 'European Euro', '€', '1.0000000000', 'Monaco'),
(127, 'EUR', 'European euro', '€', '1.0000000000', 'Netherlands'),
(128, 'NGN', 'Nigerian naira', '₦', '412.1110770000', 'Nigeria'),
(129, 'PAB', 'Panamanian balboa', 'B/.', '1.1354630000', 'Panama'),
(130, 'PLN', 'Polish zloty', 'zł', '4.3329590000', 'Poland'),
(131, 'RUB', 'Russian ruble', 'руб', '74.4828310000', 'Russia'),
(132, 'XCD', 'East Caribbean dollar', '$', '3.0681860000', 'Saint Vincent and the Grenadines'),
(133, 'EUR', 'European euro', '€', '1.0000000000', 'Slovakia'),
(134, 'TTD', 'Trinidad and Tobago dollar', 'TT$', '7.6538620000', 'Trinidad and Tobago'),
(135, 'UGX', 'Ugandan shilling', 'USh', '4266.3748230000', 'Uganda'),
(136, 'UYU', 'Uruguayan peso', '$U', '37.3058070000', 'Uruguay'),
(137, 'BTC', 'Bitcoin', '₿', '0.0001810000', 'Global');

-- --------------------------------------------------------

--
-- Table structure for table `fcm_infoo`
--

CREATE TABLE `fcm_infoo` (
  `id` varchar(183) NOT NULL,
  `fcm_token` varchar(183) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fcm_infoo`
--

INSERT INTO `fcm_infoo` (`id`, `fcm_token`) VALUES
('11891af517a255d3', 'cuNOea2JnTY:APA91bGoo5faTCHM9lg0Jg-YhLhajx8yIb5aVvdLZugFNLKDR-c-PHUvYeHvTZwkhKX3v7a8z78QbuybkUtBMzlUSro1ygrrSr8lf_CDlCsJ2PnNBmSGvhR24tqGxPxzU2v0LH78FI53'),
('7170b0ea661b1fed', 'c_x2GtzUw7M:APA91bE5rLz_hT3GBhe05dgOd7fudgz6-VQZvbdlQz6VeP3L0GeTu9oobf2KNcoXOWmU3qIemB8j87blCtwo49nfFvYVJqAO0P5YsurufIch2QAN8bgCTISF0ur8eZWeOFpTE77DtZT7');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `currency`
--
ALTER TABLE `currency`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fcm_infoo`
--
ALTER TABLE `fcm_infoo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `fcm_token` (`fcm_token`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `currency`
--
ALTER TABLE `currency`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=138;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
