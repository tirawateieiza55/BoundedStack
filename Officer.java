import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;



/** 
 *  ถิรวัฒน์ กอบแก้ว 6821651213 Sec 800
 * 
 * 
 * 
*/
public class Officer {

    private static final int Length_ID = 5;
    public static final int Max_Officer = 10;


    // AF
    // AF(ID) = เลขประจำตัวของพนักงาน ของแต่ละคน
    // AF(ID) = ID ที่ให้พนักงาน ใส่ ID เข้าออกงานได้

    // RI
    // ID ต้องไม่เป็น null
    // ID ห้ามซั้ากัน
    // ID ต้องมีแค่ 5 ตัวเลข

    // Safety from rep exposure
    // ID เป็น private  final
    // คัดลอกทั้งขาเข้าและขาออก

    private  final List<Integer> ID;
    


    private void checkRep() {

        assert ID != null : "list_ID ห้ามเป็น null" ;
        
        Set<Integer> seen = new HashSet<>();
        for (Integer s : ID) {
            assert s != null : "สมาชิกห้ามเป็น null" ;

            assert seen.add(s) : "เลข ID ห้ามซํ้า: " + s;
        }

    }
    // ===== Creater =====


    public Officer() {
        this.ID = new ArrayList<>();
        checkRep();
    }


    //Creater 2
    /**
     * 
     * @param n
     */
    public Officer(List<Integer> initial) {



        this.ID = new ArrayList<>(initial);
        checkRep();
    }


    public int size() {
        return ID.size();
    }

    public boolean contains(Integer n) {
        return this.ID.contains(n);
    }

    public List<Integer> ID() {
        return new ArrayList<>(this.ID);
    }

    public boolean add(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("เลข ID ห้ามเป็น null");
        }
        if (this.ID.contains(n)) {
            return false;
        }
        this.ID.add(n);
        checkRep();
        return true;
    }

    public boolean remove(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("เลข ID ห้ามเป็น null");
        }
        boolean removed = this.ID.remove(n);
        checkRep();
        return removed;
    }

    // Producer: คืนตัวใหม่ที่มีสมาชิกเรียงลำดับจากน้อยไปมาก
    public Officer sort() {
        List<Integer> sortedList = new ArrayList<>(this.ID);
        Collections.sort(sortedList);
        return new Officer(sortedList);
    }

    @Override
    public String toString() {
        return ID.toString();
    }


  
}
