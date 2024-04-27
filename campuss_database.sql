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

create table if not exists "Historie"
(
    id                integer  not null
        constraint Historie_pk
            primary key autoincrement,
    jmeno_studenta    TEXT     not null
        constraint Historie_jmeno_studenta_fk
            references Student (jmeno),
    prijmeni_studenta TEXT     not null
        constraint Historie_prijmeni_studenta_fk
            references Student (prijmeni),
    isic_studenta     TEXT     not null
        constraint Historie_isic_studenta_fk
            references Student (ISIC),
    satna_nazev       TEXT     not null
        constraint Historie_satna_nazev_fk
            references Satna (nazev),
    umisteni_typ      TEXT     not null
        constraint Historie_umisteni_typ_fk
            references Umisteni (typ_umisteni),
    umisteni_cislo    integer  not null
        constraint Historie_umisteni_cislo_fk
            references Umisteni (cislo),
    stav              TEXT     not null,
    cas_zmeny_stavu   DATETIME not null,
    satnarka_id       integer  not null
        constraint Historie_satnarka_id_fk
            references Satnarka,
    constraint check_stav
        check (Historie.stav = 'uschováno' OR Historie.stav = 'vyzvednuto'),
    constraint check_umisteni_typ
        check (Historie.umisteni_typ = 'věšák' OR Historie.umisteni_typ = 'podlaha')
);
COMMIT;