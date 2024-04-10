BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Satna"
(
    id_satny integer not null
        constraint Satna_pk
            primary key autoincrement,
    nazev TEXT not null
);

CREATE TABLE IF NOT EXISTS "Student"
(
    id_studenta    integer not null
        constraint Student_pk
            primary key autoincrement,
    ISIC           TEXT    not null,
    jmeno          TEXT    not null,
    prijmeni       TEXT    not null,
    email          TEXT    not null
);

CREATE TABLE IF NOT EXISTS "Umisteni"
(
    id_umisteni  integer not null
        constraint Umisteni_pk
            primary key autoincrement,
    cislo        integer not null,
    typ_umisteni TEXT    not null,
    student      TEXT, -- ISIC
    id_satny_fk  integer not null
        constraint Umisteni_satna_fk
            references Satna
                on update cascade on delete cascade,
    constraint check_typ_umisteni
        check ((typ_umisteni = 'věšák' OR typ_umisteni = 'podlaha') ),
    constraint Umisteni_student_fk
        foreign key (student)
            references Student (ISIC)
);

CREATE TABLE IF NOT EXISTS "Satnarka"
(
    id_satnarky integer not null
        constraint Satnarka_pk
            primary key autoincrement,
    id_satny_fk integer not null
        constraint Satnarka_fk
            references Satna,
    jmeno       TEXT    not null,
    prijmeni    TEXT    not null
);
COMMIT;


select * from Umisteni join satna on Umisteni.id_satny_fk = satna.id_satny where nazev = 'Italská budova';
