import java.util.*;

class Pool {
    boolean[] pool;
    int size;
    int taken;

    public Pool( int nsize ) {
        setSize( nsize );
    }

    public void reset() {
        taken = 0;

        for( int i = 0; i < size; ++i ) {
            pool[i] = false;
        }
    }

    public void setSize( int nsize ) {
        pool = new boolean[nsize];
        size = nsize;
        taken = 0;

        for( int i = 0; i < size; ++i ) {
            pool[i] = false;
        }
    }

    public int nextInt() {
        if( ( pool != null ) && ( taken < size ) ) {
            int i = bpp.rand.nextInt( size );
            
            while( pool[i] ) {
                i = bpp.rand.nextInt( size );
            }

            pool[i] = true;
            taken++;
            
            return i;
        }
        else {
            return( -1 );
        }
    }
}
