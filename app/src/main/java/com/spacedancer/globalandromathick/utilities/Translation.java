package com.spacedancer.globalandromathick.utilities;

import java.util.HashMap;
import java.util.Map;

public class Translation {
    public static String translate(String language, String text) {

        Map<String, String> trans = new HashMap<>();

        trans.put("Izračunaj:", "Solve:");

        trans.put("Jednostavnim računanjem", "By simple calculations,");
        trans.put("dolazimo do rješenja:", "we come up with a solution:");

        trans.put("x prebacimo desno, ", "Move x to the right side, ");
        trans.put(" lijevo:", " to the left:");

        trans.put("Prebacimo ", "Move ");
        trans.put(" desno:", " to the right side:");

        trans.put("Sve podijelimo sa ", "Divide both sides by ");

        trans.put("Usporedi strane:", "Compare sides:");
        trans.put(" usporedi s ", " compare with ");

        trans.put("Pitanje: ", "Question: ");

        trans.put("Ponuđeni odgovori:", "Offered answers:");
        trans.put("Odgovoreno:", "Answered:");
        trans.put("Vrijeme: ", "Time: ");
        trans.put("Bodova: ", "Score: ");
        trans.put("Rješenje:", "Solution:");




        // prijevodi za OptionsActivity
        trans.put("Opcije spremljene!", "Settings saved!");
        trans.put("Opcije", "Options");
        trans.put("Spremi", "Save");
        trans.put("Odaberi zvukove:", "Choose sounds:");
        trans.put("<= testiraj zvuk =>", "<= test sound =>");
        trans.put("Najbolja igra =>", "Best game =>");
        trans.put("<= Zadnja igra", "<= Last game");

        // game
        trans.put("Priprema!", "Ready!");
        trans.put("Pozor!", "Steady!");
        trans.put("SAD!", "GO!");
        trans.put("Mod: ", "Mode: ");
        trans.put("početni", "beginner");
        trans.put("standardni", "standard");
        trans.put("napredni", "tough");
        trans.put("isteklo!", "timed out!");



        // spinner sounds
        trans.put("Bez zvuka", "No sounds");
        trans.put("Beba", "Baby");
        trans.put("Glas", "Voice");
        trans.put("Laser", "Laser");
        trans.put("Manijak", "Maniac");
        trans.put("Pijanac", "Drunk");
        trans.put("Skok", "Jump");
        trans.put("Smijeh", "Laughter");
        trans.put("Zvjezdane staze", "Star Trek");
        trans.put("Udarac", "Kick");
        trans.put("Vjeverica", "Squirrel");


        // highscores & results activity
        trans.put("Mod: početni", "Mode: beginner");
        trans.put("Mod: standardni", "Mode: standard");
        trans.put("Mod: napredni", "Mode: taugh");

        // results activity
        trans.put("Igrač:", "Player:");
        trans.put("Bodova:", "Score:");
        trans.put("Jezik:", "Language:");
        trans.put("hrvatski", "croatian");
        trans.put("engleski", "english");
        trans.put("Zadnja igra", "Last game");
        trans.put("Najbolja igra", "Best game");

        trans.put("Molim unesi ime!", "Please enter name!");
        trans.put("Ispod granice", "Below the limit");


        trans.put("E-mail adresa je izmijenjena. Molimo prijavite se s novom adresom",
                "E-mail address is updated. Please sign in with new e-mail");
        trans.put("Izmjena e-maila nije uspjela!", "Failed to update e-mail!");
        trans.put("Unesite e-mail", "Enter e-mail");
        trans.put("Lozinka je prekratka. Unesite barem 6 znakova",
                "Password is too short. Enter minimum 6 characters");
        trans.put("Lozinka je izmijenjena. Prijavite se s novom lozinkom!",
                "Password is updated. Sign in with new password!");
        trans.put("Izmjena lozinke nije uspjela!", "Failed to update password!");
        trans.put("Unesite lozinku", "Enter password");

        trans.put("Poslan je e-mail za poništavanje lozinke!", "Reset password e-mail is sent!");
        trans.put("Slanje e-maila za poništavanje lozinke nije uspjelo!", "Failed to send reset password e-mail!");

        trans.put("Unesite e-mail za prijavu", "Enter login e-mail");

        trans.put("Vaš profil je izbrisan. Kreirajte novi korisnički račun!",
                "Your profile is deleted. Create new account!");
        trans.put("Neuspješno brisanje korisničkog računa!", "Failed to delete your account!");

        trans.put("Postavke spremljene!", "Settings saved!");

        trans.put("Autentikacija nije uspjela.", "Authentication failed.");

        trans.put("Poslane su vam instrukcije za poništavanje lozinke!",
                "We have sent you instructions to reset your password!");
        trans.put("Neuspješno slanje e-maila za poništavanje!", "Failed to send reset e-mail!");

        String translation = text;

        if (language.toUpperCase().equals("EN")) {
            if (trans.containsKey(text)) translation = trans.get(text);
        }

        return translation;
    }


    public static String dataText(String className, String language){

        String data = "";

        if (language.toUpperCase().equals("HR")){
            switch (className) {
                case "MainGameActivity":
                    data = "POČETNI EKRAN APLIKACIJE:\n\n" +

                            "Na početnom ekranu nalazi se tri gumba (1), (2) i (3) preko kojih možemo pokrenuti igru u jednom od tri moda.\n" +
                            "Postoje tri težine ili moda: početni mod (1), u kojemu se rješavaju jednostavni problemi sa malim, jednoznamenkastim brojevima. Drugi, standardni mod (2), sadržava probleme sa dvoznamenkastim, a treći, napredni mod (3), sa troznamenkastim brojevima.\n\n" +

                            "Pritiskom na 'Korisnički račun' (4) otvara se stranica za uređivanje korisničkog računa.\n\n" +

                            "Navigacijska traka: (5)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Početna stranica (otvorena stranica)\n" +
                            "- Top lista - lista najboljih rezultata\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije\n\n" +

                            "Igra:\n\n"+

                            "Igra se sastoji od dvadeset pitanja, od kojih svako ima svoj težinski faktor, na osnovu kojega se računaju bodovi. Pitanja se  mogu podijeliti na četiri cjeline:\n\n" +

                            "Prva cjelina sastoji se od šest zadataka osnova matematike - jednostavno računanje zadataka oblika a+b=c ili a-b=c, u kojemu su zadane dvije od tri nepoznanice, dok se treća, označena u zadatku sa oznakom ?, mora izračunati.\n\n" +

                            "Druga cjelina sastoji se od četiri zadatka množenja - računanje zadataka oblika a*b=c u kojemu su također zadane dvije od tri nepoznanice, dok se treća mora izračunati.\n\n" +

                            "Treća cjelina sastoji se od četiri zadatka uspoređivanja, oblika a+b usporedi s c+d, a+b usporedi s c-d, a-b usporedi s c+d ili a-b usporedi s c-d.\n\n" +

                            "Četvrta, zadnja cjelina, sastoji se od šest zadataka sa jednostavnim operacijama sa zagradama tipa a+(b-c)=?, a-(b+c)=? ili a-(b-c)=?, gdje se problem treba riješiti.\n\n" +

                            "Svaki zadatak ispisan je na gornjem dijelu ekrana, dok su ponuđena tri odgovora, od kojih je samo jedan točan. Pritiskom na točan odgovor, dobivaju se bodovi, ovisno o brzini odgovora. Pritiskom na netočan odgovor, gube se bodovi, također ovisno o brzini odgovora. Ako istekne vrijeme potrebno za odgovor, ni točan ni netočan odgovor neće utjecati na zbroj bodova. Kod početnog moda, vrijeme u kojemu se dobivaju bodovi je 15 sekundi, kod standardnog 30 sekundi, a kod naprednog 60 sekundi.\n\n\n" +

                            "Ugodnu igru uz mnogo zabave i vježbe\n" +
                            "želi vam programer Spacedancer\n" +
                            "(Mario Pavše)";
                    break;
                case "ResetPasswordActivity":
                    data = "EKRAN ZA PONIŠTAVANJE LOZINKE:\n\n" +

                            "Unosom e-mail adrese (1), te pritiskom na gumb Poništi lozinku (2), aplikacija će poslati e-mail sa poveznicom na stranicu gdje je moguće poništiti i izmijeniti lozinku za korisnički račun vezan uz tu adresu.\n\n" +

                            "Gumb 'Povratak' (3) služi za povratak na stranicu prijave.\n\n" +

                            "Navigacijska traka: (4)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Stranica za prijavu\n" +
                            "- Top lista - lista najboljih rezultata, ne otvara se dok se ne prijavite\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "SignupActivity":
                    data = "EKRAN ZA REGISTRACIJU:\n\n" +

                            "Unosom e-mail adrese (1) i lozinke (2), te pritiskom na gumb Registriraj se (3), aplikacija će kreirati korisnički račun vezan uz upisanu e-mail adresu.\n\n" +

                            "Pritiskom na gumb 'Zaboravljena lozinka?' (4) vodi na stranicu za poništavanje lozinke.\n\n" +

                            "Gumb 'Registriran sam. Prijavi me!' (5) služi za povratak na stranicu prijave.\n\n" +

                            "Navigacijska traka: (6)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Stranica za prijavu\n" +
                            "- Top lista - lista najboljih rezultata, ne otvara se dok se ne prijavite\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "LoginActivity":
                    data = "EKRAN ZA PRIJAVU:\n\n" +

                            "Unosom e-mail adrese (1) i lozinke (2), te pritiskom na gumb 'Prijava' (3), prijavljujete se u aplikaciju.\n\n" +

                            "Pritiskom na gumb 'Zaboravljena lozinka?' (4) vodi na stranicu za poništavanje lozinke.\n\n" +

                            "Gumb 'Nisi korisnik? Registriraj se sada!' (5) služi za prijelaz na stranicu registracije.\n\n" +

                            "Navigacijska traka: (6)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Stranica za prijavu (ova stranica)\n" +
                            "- Top lista - lista najboljih rezultata, ne otvara se dok se ne prijavite\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "SavingsActivity":
                    data = "EKRAN ZA PRIKAZ REZULTATA:\n\n" +

                            "Ekran za prikaz rezultata prikazuje osnovne podatke o igri - mod (1), postignuti rezultat (2) te pozicija na ljestvici najboljih rezultata (3). Ukoliko rezultat nije među najboljih 250 rezultata, umjesto pozicije prikazat će se tekst 'Ispod granice'.\n\n" +

                            "Unosom imena (4) te pritiskom na gumb 'Spremi' (5), postavit će se rezultat na listu najboljih rezultata.\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Početna stranica\n" +
                            "- Top lista - lista najboljih rezultata\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "PlayActivity":
                    data = "EKRAN ZA IGRU:\n\n" +

                            "Gornji dio ekrana za igru prikazuje osnovne podatke o pitanju - mod (1), redni broj pitanja (2), vrijeme koje je preostalo za dobivanje bodova (3) te zadatak koji je potrebno riješiti (4).\n\n" +

                            "Ponuđena su tri odgovora (5), (6) i (7), od kojih je samo jedan točan.\n\n" +

                            "Na dnu ekrana prikazuje se trenutno stanje bodova. (8)\n\n" +

                            "Igra se sastoji od dvadeset pitanja, od kojih svako ima svoj težinski faktor, na osnovu kojega se računaju bodovi. Pitanja se  mogu podijeliti na četiri cjeline:\n\n" +

                            "Prva cjelina sastoji se od šest zadataka osnova matematike - jednostavno računanje zadataka oblika a+b=c ili a-b=c, u kojemu su zadane dvije od tri nepoznanice, dok se treća, označena u zadatku sa oznakom ?, mora izračunati.\n\n" +

                            "Druga cjelina sastoji se od četiri zadatka množenja - računanje zadataka oblika a*b=c u kojemu su također zadane dvije od tri nepoznanice, dok se treća mora izračunati.\n\n" +

                            "Treća cjelina sastoji se od četiri zadatka uspoređivanja, oblika a+b usporedi s c+d, a+b usporedi s c-d, a-b usporedi s c+d ili a-b usporedi s c-d.\n\n" +

                            "Četvrta, zadnja cjelina, sastoji se od šest zadataka sa jednostavnim operacijama sa zagradama tipa a+(b-c)=?, a-(b+c)=? ili a-(b-c)=?, gdje se problem treba riješiti.\n\n" +

                            "Pritiskom na točan odgovor, dobivaju se bodovi, ovisno o brzini odgovora. Pritiskom na netočan odgovor, gube se bodovi, također ovisno o brzini odgovora. Ako istekne vrijeme potrebno za odgovor, ni točan ni netočan odgovor neće utjecati na zbroj bodova. Kod početnog moda, vrijeme u kojemu se dobivaju bodovi je 15 sekundi, kod standardnog 30 sekundi, a kod naprednog 60 sekundi.\n\n" +

                            "Navigacijska traka: (9)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Početna stranica\n" +
                            "- Top lista - lista najboljih rezultata\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "ResultsActivity":
                    data = "STRANICA DETALJA IGRE:\n\n" +

                            "Stranica detalja igre prikazuje sve podatke o igri. Na vrhu ekrana (1) prikazani su osnovni podaci o igri - da li se radi o zadnjoj igri ili najboljoj igri, ime igrača, broj bodova, mod te odabrani jezik za tu igru.\n\n" +

                            "Ispod osnovnih podataka nalaze se detaljni podaci o svakom od pitanja - broju pitanja (2), slika palca prema gore ili prema dolje (3), ovisno o tome da li je točno odgovoreno, postavljeno pitanje (4), ponuđeni odgovori (5), osnovni podaci o odgovoru (6) - što je odgovoreno, dobiveni bodovi te vrijeme u kojemu je odgovoreno i na kraju cijeli postupak rješavanja zadatka (7).\n\n" +

                            "Navigacijska traka: (8)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Početna stranica\n" +
                            "- Top lista - lista najboljih rezultata\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "OptionsActivity":
                    data = "STRANICA OPCIJA:\n\n" +

                            "Na stranici opcija moguće je odabrati jezik aplikacije, koji može biti hrvatski ili engleski (1).\n\n" +

                            "Tema zvukova koji će označavati točan ili netočan odgovor može se odabrati pritiskom na gumb (2), koji je u stvari padajući izbornik, u kojemu je moguće odabrati jednu od 10 tema. Zvukovi se mogu testirati pritiskom na palac gore (4) koji označava zvuk za točan odgovor, ili palac dolje (3) za netočan odgovor. Pritiskom na gumb 'Spremi' (5) pospremaju se odabrane opcije.\n\n" +

                            "Ispod teme zvukova nalazi se klizač u kojemu možemo podesiti jačinu zvuka igre (6). 'Min' predstavlja najtiše, 'Max' najglasnije, a u sredini je prikazana glasnoća u postocima.\n\n" +

                            "Na dnu ekrana nalaze se dva odjeljka - za zadnju igru (7) i za najbolju igru (8). Svaki od tih odjeljaka sadrži tri gumba, koji vode na stranice detalja igre po određenom modu.\n\n" +

                            "Navigacijska traka: (9)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Početna stranica\n" +
                            "- Top lista - lista najboljih rezultata\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije (ovaj ekran)\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "LeaderboardActivity":
                    data = "STRANICA TOP LISTE REZULTATA:\n\n" +

                            "Ova stranica prikazuje 250 najboljih rezultata za određeni mod igre. Na vrhu se nalaze tri gumba (1) za odabir moda za koji se žele vidjeti rezultate. Mod je prikazan ispod glavnog naslova. (2)\n\n" +

                            "Najveći dio ekrana zauzima tablica najboljih rezultata (3), koja prikazuje mjesto, ime i osvojene bodove.\n\n" +

                            "Navigacijska traka: (4)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Početna stranica\n" +
                            "- Top lista (ovaj ekran)\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;
                case "MainActivity":
                    data = "STRANICA KORISNIČKOG RAČUNA\n\n" +

                            "Ova stranica služi za izmjenu korisničkog računa. Moguće je promijeniti e-mail adresu (2), lozinku (3) te poništiti lozinku preko e-maila (4). Pritiskom na jedan od ova tri gumba, prikazat će se na vrhu ekrana (1) obrazac za unos e-maila ili lozinke te gumb za izmjenu ili slanja e-maila za poništavanje lozinke.\n\n" +

                            "U donjem dijelu ekrana nalazi se gumb za brisanje korisničkog računa koji je prijavljen (5), te gumb za odjavu iz aplikacije (6).\n\n" +

                            "Navigacijska traka: (7)\n\n" +

                            "Navigacijska traka se sastoji od 5 ikona koje redom vode na sljedeće stranice aplikacije:\n" +
                            "- Početna stranica\n" +
                            "- Top lista - lista najboljih rezultata\n" +
                            "- Pomoć - pomoć za svaku stranicu na kojoj se nalazite\n" +
                            "- Opcije - postavke aplikacije\n" +
                            "- Ikona za zatvaranje aplikacije";
                    break;

            }
        } else {
            switch (className) {
                case "MainGameActivity":
                    data = "APPLICATION HOME SCREEN:\n\n" +

                            "The home screen has three buttons (1), (2) and (3) through which we can start the game in one of three modes.\n" +
                            "There are three difficulty or modes: beginner's mode (1), in which we solve simple problems with small, single-digit numbers. The second, standard mode (2) contains problems with double-digit, and the third, tough mode (3) with three-digit numbers.\n\n" +

                            "Clicking on 'User account' (4) opens the screen for editing the account.\n\n" +

                            "Navigation bar: (5)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Home screen (this screen)\n" +
                            "- Leaderboard - list of best results\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon\n\n" +

                            "Game:\n\n"+

                            "The game consists of twenty questions, each with its own weight factor, on which points are calculated. The questions can be divided into four sections:\n\n" +

                            "The first group consists of six basic mathematical problems - a simple calculation of tasks of the form a+b=c or a-b=c, in which are given two of three unknowns, while the third, replaced in the task with ?, must be calculated.\n\n" +

                            "The second group consists of four multiplication tasks - computing tasks of the form a*b=c in which two of the three unknowns are also given, while the third one must be calculated.\n\n" +

                            "The third group consists of four comparison tasks, the form a+b compare with c+d, a+b compare with c-d, a-b compare with c+d or a-b compare with c-d.\n\n" +

                            "The last, fourth part, consists of six tasks with simple operations with parentheses of type a+(b-c)=?, a-(b+c)=? or a-(b-c)= ?, where the problem should be solved.\n\n" +

                            "Each question is displayed at the top of the screen, while three answers are offered, only one of which is correct. Pressing the correct answer will give you points, depending on the speed of the answer. Clicking on the wrong answer will lose points, also depending on the speed of the answer. If the time required for a response expires, neither the correct nor the incorrect answer will affect the sum of points. In the beginner's mode, the time in which points are awarded is 15 seconds, in the standard mode 30 seconds, and in the advanced mode 60 seconds.\n\n\n" +

                            "Have a good game with lot of fun and exercise!\n" +
                            "Your developer, Spacedancer\n" +
                            "(Mario Pavše)";
                    break;
                case "ResetPasswordActivity":
                    data = "RESET PASSWORD SCREEN:\n\n" +

                            "By entering the e-mail address (1) and pressing the Reset password button (2), the application will send an e-mail with a link to the page where it is possible to reset and change the password for the user account associated with that address.\n\n" +

                            "The 'Back' button (3) is used to return to the login screen.\n\n" +

                            "Navigation bar: (4)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Login screen\n" +
                            "- Leaderboard - list of best results, it doesn't open until you sign in\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
                case "SignupActivity":
                    data = "SIGN UP SCREEN:\n\n" +

                            "By entering your email address (1) and password (2), and pressing the 'Register' button (3), the application will create an account related to the entered email address.\n\n" +

                            "By clicking on the 'Forgot your password?' (4) leads to the reset password screen.\n\n" +

                            "The 'Already registered. Login Me!' button (5) is used to return to the login screen.\n\n" +

                            "Navigation bar: (6)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Login screen\n" +
                            "- Leaderboard - list of best results, it doesn't open until you sign in\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
                case "LoginActivity":
                    data = "LOGIN SCREEN:\n\n" +

                            "By entering your email address (1) and password (2), and pressing the 'Login' button (3), you are logged into the application.\n\n" +

                            "By clicking on the 'Forgot your password?' (4) leads to the reset password screen.\n\n" +

                            "The 'Not a member? Get registered in Firebase!' button (5) navigates to the sign up screen.\n\n" +

                            "Navigation bar: (6)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Login screen (this screen)\n" +
                            "- Leaderboard - list of best results, it doesn't open until you sign in\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
                case "SavingsActivity":
                    data = "RESULTS SCREEN:\n\n" +

                            "The results screen displays basic game information - mode (1), score (2), and position on the leaderboard (3). If the result is not among the top 250 results, the text 'Below the limit' will be displayed instead of the position.\n\n" +

                            "Entering a name (4) and pressing the 'Save' button (5) will place the result on the list of best results.\n\n" +

                            "Navigation bar: (6)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Home screen\n" +
                            "- Leaderboard - list of best results\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
                case "PlayActivity":
                    data = "PLAY SCREEN:\n\n" +

                            "The upper part of the play screen shows basic information about the question - mode (1), the number of question (2), the time remaining to receive points (3), and the problem to be solved (4).\n\n" +

                            "Three answers (5), (6) and (7) were offered, only one of which is correct.\n\n" +

                            "The current status of the points is displayed at the bottom of the screen. (8)\n\n" +

                            "The game consists of twenty questions, each with its own weight factor, on which points are calculated. The questions can be divided into four sections:\n\n" +

                            "The first group consists of six basic mathematical problems - a simple calculation of tasks of the form a+b=c or a-b=c, in which are given two of three unknowns, while the third, replaced in the task with ?, must be calculated.\n\n" +

                            "The second group consists of four multiplication tasks - computing tasks of the form a*b=c in which two of the three unknowns are also given, while the third one must be calculated.\n\n" +

                            "The third group consists of four comparison tasks, the form a+b compare with c+d, a+b compare with c-d, a-b compare with c+d or a-b compare with c-d.\n\n" +

                            "The last, fourth part, consists of six tasks with simple operations with parentheses of type a+(b-c)=?, a-(b+c)=? or a-(b-c)= ?, where the problem should be solved.\n\n" +

                            "Pressing the correct answer will give you points, depending on the speed of the answer. Clicking on the wrong answer will lose points, also depending on the speed of the answer. If the time required for a response expires, neither the correct nor the incorrect answer will affect the sum of points. In the beginner's mode, the time in which points are awarded is 15 seconds, in the standard mode 30 seconds, and in the advanced mode 60 seconds.\n\n" +

                            "Navigation bar: (9)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Home screen\n" +
                            "- Leaderboard - list of best results\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
                case "ResultsActivity":
                    data = "THE GAME DETAILS SCREEN:\n\n" +

                            "The game details page displays all game information. The top of the screen (1) shows basic game information - whether it is the last game or the best game, the name of the player, the score, the mode and the language selected for that game.\n\n" +

                            "Below the basic information you will find detailed information about each question - questionnumber (2), thumb up or down picture (3) depending on whether it was answered correctly, question asked (4), answers offered (5), basic answer data (6) - what was answered, points scored, and the time it took to answer, and finally the whole process of solving the problem (7).\n\n" +

                            "Navigation bar: (8)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Home screen\n" +
                            "- Leaderboard - list of best results\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
                case "OptionsActivity":
                    data = "OPTIONS SCREEN:\n\n" +

                            "On the options page, you can select the language of the application, which can be Croatian or English (1).\n\n" +

                            "The sounds theme that will indicate correct or incorrect answer can be selected by pressing the button (2), which is actually a drop-down menu, in which one of 10 topics can be selected. Sounds can be tested by pressing the thumbs up (4) indicating the sound for the correct answer, or thumbs down (3) for the incorrect answer. Pressing the 'Save' button (5) saves the selected options.\n\n" +

                            "Below the sounds theme option is a slider in which we can adjust the sound volume of the game (6). 'Min' is the quietest, 'Max' is the loudest, and in the middle is the percentage of the volume.\n\n" +

                            "There are two sections at the bottom of the screen - for the last game (7) and for the best game (8). Each of these sections contains three buttons, which lead to game details screens in a specific mode.\n\n" +

                            "Navigation bar: (9)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Home screen\n" +
                            "- Leaderboard - list of best results\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options (this screen)\n" +
                            "- Close application icon";
                    break;
                case "LeaderboardActivity":
                    data = "HIGH SCORES TABLE SCREEN:\n\n" +

                            "This page shows the top 250 results for a particular game mode. At the top are three buttons (1) to select the mode for which you want to see results. The mode is shown below the main title. (2)\n\n" +

                            "the largest part of the screen is occupied by the highest score table (3), which shows the rank, players name and points scored.\n\n" +

                            "Navigation bar: (4)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Home screen\n" +
                            "- Leaderboard (this screen)\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
                case "MainActivity":
                    data = "USER ACCOUNT SCREEN\n\n" +

                            "This page is used to change the account data. It is possible to change the e-mail address (2), password (3) and reset the password via e-mail (4). Pressing one of these three buttons will display at the top of the screen (1) an email or password entry form and a button for changing or sending an e-mail to reset the password.\n\n" +

                            "At the bottom of the screen there is a button to delete the logged in account (5), and a button to sign out of the application (6).\n\n" +

                            "Navigation bar: (7)\n\n" +

                            "The navigation bar consists of 5 icons that lead to the following screens of the application:\n" +
                            "- Home screen\n" +
                            "- Leaderboard - list of best results\n" +
                            "- Help - help for every screen you are on\n" +
                            "- Options - application settings\n" +
                            "- Close application icon";
                    break;
            }
        }

        return data;

    }

}
