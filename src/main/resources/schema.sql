use `auction-db`;

CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    created       DATETIME NOT NULL, -- Ημερομηνία δημιουργίας εγγραφής (συστημικό)
    username      VARCHAR(100),
    password      VARCHAR(100),
    name          VARCHAR(100),
    surname       VARCHAR(100),
    email         VARCHAR(100),
    phone         VARCHAR(15),       -- τηλέφωνο επικοινωνίας
    afm           VARCHAR(9),        -- αριθμός φορολογικού μητρώου
    bidder_rating INT,               -- το rating του χρήστη ως bidder
    seller_rating INT,               -- το rating του χρήστη ως seller
    location      VARCHAR(100),
    country       VARCHAR(3),        -- 3-letter country codes stored
    avatar        MEDIUMBLOB         -- https://www.dbvis.com/thetable/blob-data-type-everything-you-can-do-with-it/#:~:text=BLOB%20%3A%20Can%20store%20up%20to,to%204%2C294%2C967%2C295%20bytes%20of%20data.
);

CREATE TABLE IF NOT EXISTS roles
(
    name        ENUM ('SELLER','BIDDER','GUEST','ADMIN') PRIMARY KEY,
    description VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_roles -- many-to-many relationship
(
    user_id   BIGINT                                   NOT NULL,
    role_name ENUM ('SELLER','BIDDER','GUEST','ADMIN') NOT NULL,
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES roles (name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS categories
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS auctions
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY, -- Μοναδικό id της δημοπρασίας,
    title            VARCHAR(500)   NOT NULL,           -- τίτλος της δημοπρασίας
    created          DATETIME       NOT NULL,           -- Ημερομηνία δημιουργίας εγγραφής (συστημικό)
    started          DATETIME       NULL,               -- Χρόνος έναρξης της δημοπρασίας
    end_date         DATETIME       NOT NULL,           -- Χρόνος λήξης της δημοπρασίας
    first_bid        DECIMAL(10, 2) NOT NULL,           -- το ελάχιστο μέγεθος της πρώτης προσφοράς, το οποίο ορίζεται από τον πωλητή πριν την έναρξη της δημοπρασίας
    current_best_bid DECIMAL(10, 2) NULL,               -- Η τρέχουσα καλύτερη προσφορά σε δολάρια. Είναι πάντοτε ίση με την υψηλότερη προσφορά ή με το First_Bid αν δεν έχουν υποβληθεί προσφορές
    number_of_bids   INT            NOT NULL,           -- αριθμός των προσφορών / των στοιχείων προσφοράς, το οποίο ορίζεται από τον πωλητή πριν την έναρξη της δημοπρασίας
    seller_id        BIGINT         NOT NULL,           -- Ο πωλητής / δημιουργός της δημοπρασίας
    category_id      BIGINT         NOT NULL,           -- Η Κατηγορία της δημοπρασίας
    FOREIGN KEY (seller_id) REFERENCES users (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS auction_items
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY, -- Μοναδικό id για το αντικείμενο που τίθεται σε δημοπρασία
    auction_id  BIGINT,
    name        VARCHAR(255) NOT NULL,             -- Μια σύντομη ονομασία που χρησιμοποιείται ως περιγραφή της δημοπρασίας
    description TEXT,                              -- Η πλήρης περιγραφή του αντικειμένου
    location    VARCHAR(100),                      -- Τα στοιχεία Location και Country εντός του item περιλαμβάνουν τις γεωγραφικές πληροφορίες του αντικειμένου.
    latitude    DECIMAL(10, 7),                    -- Προαιρετικά τα attributes Latitude και Longitude του στοιχείου Location ορίζουν τις γεωγραφικές συντεταγμένες του αντικειμένου.
    longitude   DECIMAL(10, 7),
    country     VARCHAR(3),                        -- 3-letter country codes stored
    image       MEDIUMBLOB,
    FOREIGN KEY (auction_id) REFERENCES auctions (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS item_categories
( -- many-to-many relationship
    auction_item_id BIGINT NOT NULL,
    category_id     BIGINT NOT NULL,
    PRIMARY KEY (auction_item_id, category_id),
    FOREIGN KEY (auction_item_id) REFERENCES auction_items (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS item_categories
( -- many-to-many relationship
    auction_item_id BIGINT NOT NULL,
    category_id     BIGINT NOT NULL,
    PRIMARY KEY (auction_item_id, category_id),
    FOREIGN KEY (auction_item_id) REFERENCES auction_items (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bids
(                                           -- προσφορές
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    auction_id     BIGINT         NOT NULL, -- η δημοπρασία για την οποία κάνει τη προσφορά
    bidder_id      BIGINT         NOT NULL, -- indicates the user id
    time_submitted DATETIME       NOT NULL, -- Χρόνος υποβολής της προσφοράς. Πρέπει να είναι μεταγενέστερο του χρόνου έναρξης της ψηφοφορίας και προγενέστερο του χρόνου λήξης της. Ένας χρήστης μπορεί να υποβάλλει πολλαπλές προσφορές σε μια δημοπρασία αλλά σε διαφορετικό χρόνο.
    amount         DECIMAL(10, 2) NOT NULL, -- το ποσό της προσφοράς
    FOREIGN KEY (auction_id) REFERENCES auctions (id),
    FOREIGN KEY (bidder_id) REFERENCES users (id)
);