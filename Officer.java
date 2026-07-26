import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Officer {

    private static final int Length_ID = 5;


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

    private  final ArrayList<Integer> ID;
    


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
    public Officer(Integer n) {
        
        checkRep();
    }


  
}
