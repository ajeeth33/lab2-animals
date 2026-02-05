import java.util.Random;

abstract class Animal 
{
    char s;
    Animal(char s) 
    { 
      this.s = s;
    }
    Animal baby() 
    { 
      return (s == 'B') ? new Bear() : new Fish(); 
    }
}
class Bear extends Animal 
{ 
  Bear() 
  { 
    super('B'); 
  } 
}
class Fish extends Animal 
{ 
  Fish()
  { 
    super('F'); 
  } 
}

public class Ecosystem 
{
    static Random r = new Random();

    static void print(Animal[] river) 
  {
        for (Animal a : river) System.out.print(a == null ? "-" : a.s);
        System.out.println();
    }

    static int randomEmpty(Animal[] river) 
  {
        int tries = river.length * 2;
        while (tries-- > 0) 
        {
            int i = r.nextInt(river.length);
            if (river[i] == null) return i;
        }
        for (int i = 0; i < river.length; i++) if (river[i] == null) return i;
        return -1;
    }

    static void step(Animal[] river) 
  {
        boolean[] done = new boolean[river.length];

        for (int i = 0; i < river.length; i++) 
        {
            if (river[i] == null || done[i]) continue;

            int move = r.nextInt(3) - 1;          
            int j = i + move;
            if (j < 0 || j >= river.length) j = i; 
            if (j == i) 
            { 
              done[i] = true; continue; 
            }

            Animal a = river[i];

            if (river[j] == null) 
            {                
                river[j] = a;
                river[i] = null;
                done[j] = true;
            } 
            else 
            {
                Animal b = river[j];

                if (a.s == b.s) 
                {                 
                    int k = randomEmpty(river);
                    if (k != -1) river[k] = a.baby();
                    done[i] = true;
                    done[j] = true;
                } 
                else if (a.s == 'B' && b.s == 'F')
                { 
                    river[j] = a;
                    river[i] = null;
                    done[j] = true;
                } 
                else if (a.s == 'F' && b.s == 'B') 
                { 
                    river[i] = null;
                    done[i] = true;
                }
            }
        }
    }

    public static void main(String[] args) 
  {
        int n = 30;
        Animal[] river = new Animal[n];

        for (int i = 0; i < n; i++) 
        {
            int x = r.nextInt(10);      
            if (x == 0) river[i] = new Bear();  
            else if (x <= 2) river[i] = new Fish(); 
        }

        print(river);
        for (int t = 1; t <= 15; t++) { 
            step(river);
            print(river);
        }
    }
}
