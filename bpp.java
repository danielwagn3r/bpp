import java.io.*;
import java.util.*;
import gnu.getopt.*;

public class bpp {

    static int    gen = 50;
    static int    sp  = 4;
    static int    ps  = 20;
    static double rp  = 0.8;
    static double mp  = 0.5;
    static int    mr  = 1;
    static int    sel = 'a';
    static boolean elitism = false;
    static String fname;
    
    static double   wmax;
    static int      n;

    static double[] data;
    static Random   rand;
    static Dna[]    pop;
    
    public static void main( String[] args ) {
        int c;
        String arg;
        boolean nflag = false, eflag = false, gflag = false, mflag = false, pflag = false, rflag = false, sflag = false, lflag = false;
        rand = new Random();

        
        // Parameter von Commandozeile auswerten
    
        LongOpt[] longopts = new LongOpt[7];
                
        longopts[0] = new LongOpt( "help",          LongOpt.NO_ARGUMENT,       null, 'h' );
        longopts[1] = new LongOpt( "population",    LongOpt.REQUIRED_ARGUMENT, null, 'p' );
        longopts[2] = new LongOpt( "generations",   LongOpt.REQUIRED_ARGUMENT, null, 'g' );
        longopts[3] = new LongOpt( "pressure",      LongOpt.REQUIRED_ARGUMENT, null, 's' );
        longopts[4] = new LongOpt( "mutprop",       LongOpt.REQUIRED_ARGUMENT, null, 'm' );
        longopts[4] = new LongOpt( "mutrate",       LongOpt.REQUIRED_ARGUMENT, null, 'n' );
        longopts[5] = new LongOpt( "recombprop",    LongOpt.REQUIRED_ARGUMENT, null, 'r' );
        longopts[6] = new LongOpt( "recombalg",     LongOpt.REQUIRED_ARGUMENT, null, 'l' );
        longopts[6] = new LongOpt( "elitism",       LongOpt.REQUIRED_ARGUMENT, null, 'e' );
        
        Getopt g = new Getopt( "bpp", args, "hep:g:s:m:r:l:n:", longopts );
        
        if ( args.length >= 1 ) {
            while ( ( c = g.getopt() ) != -1 ) {
                switch ( c ) {
                case 'g':
                    if ( gflag )
                        usage();
                    else {
                        arg = g.getOptarg();
                        gen = ( new Integer( arg ) ).intValue();
                        gflag = true;
                    }
                
                    break;
            
                case 'n':
                    if ( nflag )
                        usage();
                    else {
                        arg = g.getOptarg();
                        mr = ( new Integer( arg ) ).intValue();
                        nflag = true;
                    }
                
                    break;
            
                case 'l':
                    if ( lflag )
                        usage();
                    else {
                        arg = g.getOptarg();

                        if (  arg.equals( "b" ) )
                            sel = 'a';
                        else if ( arg.equals( "b" ) )
                            sel = 'b';
                        lflag = true;
                    }
                
                    break;
                    
                case 'm':
                    if ( mflag )
                        usage();
                    else {
                        arg = g.getOptarg();
                        mp = ( new Double( arg ) ).doubleValue();
                        mflag = true;
                    }
                
                    break;
            
                case 'p':
                    if ( pflag )
                        usage();
                    else {
                        arg = g.getOptarg();
                        ps = ( new Integer( arg ) ).intValue();
                        pflag = true;
                    }
                
                    break;
            
                case 'r':
                    if ( rflag )
                        usage();
                    else {
                        arg = g.getOptarg();
                        rp = ( new Double( arg ) ).doubleValue();
                        rflag = true;
                    }
                
                    break;
            
                case 's':
                    if ( sflag )
                        usage();
                    else {
                        arg = g.getOptarg();
                        sp = ( new Integer( arg ) ).intValue();
                        sflag = true;
                    }
                
                    break;
            
                case 'e':
                    if ( eflag )
                        usage();
                    else {
                        elitism = true;
                        eflag = true;
                    }
                
                    break;

                case 'h':
                case '?':
                    usage();
                    break;

                default:
                    usage();
                    break;
                }
            }

            if ( g.getOptind() < args.length )
                fname = args[g.getOptind()];
            else
                usage();
        }
        else
            usage();


        // Ausgabe der eingestellten Optionen
    
        System.out.println( "bpp: Options" );
        System.out.println( "  Generations:               " + gen );
        System.out.println( "  Mutation-Propapility:      " + mp );
        System.out.println( "  Mutation-Rate:             " + mr );
        System.out.println( "  Size of population:        " + ps );
        System.out.println( "  Recombination-Propapility: " + rp );
        System.out.println( "  Recombination-Algorithm    " + (char)sel );
        System.out.println( "  Selection-Pressure:        " + sp );
        System.out.println( "  Elitism:                   " + elitism );
        System.out.println( "  File:                      " + fname );
        System.out.println();

        // Daten laden
        load();

        // Population initialisieren
        init();

        // Aktuelle Population ausgeben
        population( true );

        // Generationen durchlaufen
        for( int i = 0; i < gen; i++ ) {
            System.out.println( "bpp: Generation " + i );
            
            // Selektion
            selection();

             // Rekombination
            recombination();
            
            // Mutation
            mutation();

            // Aktuelle Population ausgeben
            population( false );

            System.out.println();
        }
    }


    static void population( boolean all ) {
        double fit = 0.0, avg = 0.0, sum = 0.0;
        double max = Double.MIN_VALUE;
        int    best = 0;

        System.out.println( "bpp: Population" );

        for ( int i = 0; i < ps; i++ ) {
            if( all )
                System.out.println( pop[i].toString() );
            
            fit  = pop[i].fitness();
            sum += fit;
            
            if( fit > max ) {
                max  = fit;
                best = i;
            }
        }

        avg = sum / ps;

        if( !all )
            System.out.println( "  " + pop[best].toString() );
        
        System.out.println( "  Maximum: " + max );
        System.out.println( "  Average: " + avg );
        System.out.println();
    }
    
    
    static void usage() {
        System.out.println( "Usage: bpp [Options] file" );
        
        System.out.println( "\nOptions:\n" );
        System.out.println( "  -g, --generations <int>             Count of generations [Default: 50]" );
        System.out.println( "  -m, --mutprop <double>              Mutation-Propability [Default: 0.5]" );
        System.out.println( "  -n, --mutrate <int>                 Mutation-Rate [Default: 1]" );
        System.out.println( "  -p, --populationsize <int>          Size of population [Default: 20]" );
        System.out.println( "  -r, --recombprop <double>           Recombination-Propability [Default: 0.8]" );
        System.out.println( "  -l, --recombalg <a|b>               Recombination-Algorithm [Default: a]" );
        System.out.println( "  -s, --pressure <int>                Selection-Pressure [Default: 4]" );
        System.out.println( "  -e, --elitism                       Elitism [Default: false]" );
        System.out.println( "  -h, --help                          Display this help screen" );

        System.out.println( "\nArguments:\n" );
        System.out.println( "  file                                File to load" );

        System.exit( 1 );
    }


    static void load() {
        File file;
        BufferedReader fileReader;
        String line;

        try {
            file = new File( fname );

            if ( !file.exists() ) {
                System.err.println( "bpp: File '" + fname + "' doesn't exist!" );
            }
            else if ( !file.canRead() ) {
                System.err.println( "bpp: File '" + fname + "' couldn't be read!" );
            }
            else {
                fileReader = new BufferedReader( new FileReader( file ) );

                line = fileReader.readLine();

                StringTokenizer tokenizer = new StringTokenizer( line );

                if ( tokenizer.hasMoreTokens() ) {
                    wmax = ( new Double( tokenizer.nextToken() ) ).doubleValue();
                }
                else {
                    System.err.println( "bpp: File '" + fname + "' currupted!" );
                    System.exit( 2 );
                }

                if ( tokenizer.hasMoreTokens() ) {
                    n = ( new Integer( tokenizer.nextToken() ) ).intValue();
                }
                else {
                    System.err.println( "bpp: File '" + fname + "' currupted!" );
                        System.exit( 2 );
                }

                System.out.println( "bpp: File-Information" );
                System.out.println( "  Wmax:                      " + wmax );
                System.out.println( "  n:                         " + n );

                data = new double[n];
                
                for ( int i = 0; i < n; i++ ) {
                    line = fileReader.readLine();
                    
                    tokenizer = new StringTokenizer( line );
                    
                    if ( tokenizer.hasMoreTokens() ) {
                        data[i] = ( new Double( tokenizer.nextToken() ) ).doubleValue();
                    }
                    else {
                        System.err.println( "bpp: File '" + fname + "' currupted!" );
                        System.exit( 2 );
                    }
                }
            }

            System.out.println();
        }
        catch ( IOException e ) {
            System.err.println( e.getMessage() );
            System.exit( 2 );
        }
    }

    
    static void init() {
        System.out.println( "bpp: Initialisation" );

        pop = new Dna[ps];
        
        for( int i = 0; i < ps; ++i ) {
            pop[i] = new Dna( true );
        }
        
        System.out.println();
    }

    
    static void selection() {
        Dna npop[]  = new Dna[ps];
        double fit  = 0.0;
        double max  = Double.MIN_VALUE;
        int    best = 0, c;

        // einfache tournament selection
        for( int i = 0; i < ps; ++i ) {
            max = Double.MIN_VALUE;

            for( int j = 0; j < sp; ++j ) {
                c = rand.nextInt( ps );
                
                fit = pop[c].fitness();

                if( fit > max ) {
                    best = c;
                    max  = fit;
                }
            }

            npop[i] = (Dna) pop[best].clone();
        }

        pop = npop;
    }
    
    static void mutation() {
        // In jeder Generation 'mr' Mutationen versuchen
        
        for( int i = 0; i < mr; ++i ) {
            // Mit Wahrscheinlichkeit 'mp' an einer zufälligen Dna eine Mutation durchführen

            if( rand.nextDouble() < mp ) {
                pop[rand.nextInt(ps)].mutate();
            }
        }
    }
    
    static void recombination() {
        Dna npop[] = new Dna[ps];
        int a, b;
        int i = 0;

        while( i < ps ) {
            a = rand.nextInt( ps );
            b = rand.nextInt( ps );

            if( rand.nextDouble() < rp ) {
                if( sel == 'a' )
                    npop[i] = Dna.recombine_a( pop[a], pop[b] );
                else
                    npop[i] = Dna.recombine_b( pop[a], pop[b] );
                ++i;
            }
        }

        pop = npop;
    }
}
