CREATE TABLE users (
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
    country       VARCHAR(3)         -- 3-letter country codes stored
);

CREATE TABLE auctions (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY, -- Μοναδικό id της δημοπρασίας
    created        DATETIME       NOT NULL,           -- Ημερομηνία δημιουργίας εγγραφής (συστημικό)
    started        DATETIME       NULL,               -- Χρόνος έναρξης της δημοπρασίας
    ends           DATETIME       NOT NULL,           -- Χρόνος λήξης της δημοπρασίας
    first_bid      DECIMAL(10, 2) NOT NULL,           -- το ελάχιστο μέγεθος της πρώτης προσφοράς, το οποίο ορίζεται από τον πωλτή πριν την έναρξη της δηοπρασίας
    currently      DECIMAL(10, 2) NULL,               -- Η τρέχουσα καλύτερη προσφορά σε δολάρια. Είναι πάντοτε ίση με την υψηλότερη προσφορά ή με το First_Bid αν δεν έχουν υποβληθεί προσφορές
    number_of_bids INT            NOT NULL,           -- αριθμός των προσφορών / των στοιχείων προσφοράς, το οποίο ορίζεται από τον πωλητή πριν την έναρξη της δημοπρασίας
    seller_id      BIGINT         NOT NULL,           -- Ο πωλητής / δημιουργός της δημοπρασίας
    FOREIGN KEY (seller_id) REFERENCES users(id)
);

CREATE TABLE auction_items (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY, -- Μοναδικό id για το αντικείμενο που τίθεται σε δημοπρασία
    auction_id  BIGINT,
    name        VARCHAR(255) NOT NULL,             -- Μια σύντομη ονομασία που χρησιμοποιείται ως περιγραφή της δημοπρασίας
    description TEXT,                              -- Η πλήρης περιγραφή του αντικειμένου
    location    VARCHAR(100),                      -- Τα στοιχεία Location και Country εντός του item περιλαμβάνουν τις γεωγραφικές πληροφορίες του αντικειμένου.
    latitude    DECIMAL(10, 7),                    -- Προαιρετικά τα attributes Latitude και Longitude του στοιχείου Location ορίζουν τις γεωγραφικές συντεταγμένες του αντικειμένου.
    longitude   DECIMAL(10, 7),
    country     VARCHAR(3),                        -- 3-letter country codes stored
    FOREIGN KEY (auction_id) REFERENCES auctions(id)
);

CREATE TABLE item_image (              -- one-to-one relationship
    item_id     BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL, -- Μια σύντομη ονομασία που χρησιμοποιείται ως περιγραφή της δημοπρασίας
    description VARCHAR(255),          -- Η πλήρης περιγραφή του αντικειμένου,
    type        VARCHAR(4),            -- ο τύπος της εικόνας (JPEG, PNG etc)
    image       BLOB,                  -- 65,535 bytes
    PRIMARY KEY (item_id)
);

CREATE TABLE categories (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE item_categories ( -- many-to-many relationship
    auction_item_id BIGINT NOT NULL,
    category_id     BIGINT NOT NULL,
    PRIMARY KEY (auction_item_id, category_id),
    FOREIGN KEY (auction_item_id) REFERENCES auction_items(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE bids (                     -- προσφορές
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    auction_id BIGINT         NOT NULL, -- η δημοπρασία για την οποία κάνει τη προσφορά
    bidder_id  BIGINT         NOT NULL, -- indicates the user id
    time       DATETIME       NOT NULL, -- Χρόνος υποβολής της προσφοράς. Πρέπει να είναι μεταγενέστερο του χρόνου έναρξης της ψηφοφορίας και προγενέστερο του χρόνου λήξης της. Ένας χρήστης μπορεί να υποβάλλει πολλαπλές προσφορές σε μια δημοπρασία αλλά σε διαφορετικό χρόνο.
    amount     DECIMAL(10, 2) NOT NULL, -- το ποσό της προσφοράς
    FOREIGN KEY (auction_id) REFERENCES auctions(id),
    FOREIGN KEY (bidder_id) REFERENCES users(id)
);