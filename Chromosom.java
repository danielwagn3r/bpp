import java.util.*;

class Chromosom extends Object implements Cloneable {
    Vector gens;
    
    public Chromosom() {
        gens = new Vector();
    }

    public double weight() {
        double g = 0.0;
        
        for( int j = 0; j < gens.size(); ++j ) {
            g += bpp.data[( (Integer)gens.get( j ) ).intValue()];
        }

        return( g );
    }

    public void add( int i ) {
        gens.add( new Integer( i ) );
    }

    public void remove( int i ) {
        Integer g;

        for( int j = 0; j < gens.size(); ++j ) {
            g = (Integer)gens.get( j );

            if( g.intValue() == i ) {
                gens.remove( g );
            }
        }
    }

    public int get( int i ) {
        return ( (Integer) gens.get( i ) ).intValue();
    }

    public int size() {
        return( gens.size() );
    }

    public String toString() {
        String out = "";
        
        for( int j = 0; j < gens.size(); ++j ) {
            if( j > 0 )
                out += " " + gens.get( j );
            else
                out += gens.get( j );
        }

        return( out );
    }

    public Object clone() {
        Chromosom c = new Chromosom();

        for( int j = 0; j < gens.size(); ++j ) {
            c.add( get( j ) );
        }

        return( c );
    }

    public int[] toArray() {
        int a[] = new int[gens.size()];

        for( int i = 0; i < gens.size(); ++i ) {
            a[i] = get( i );
        }

        return a;
    }
}
