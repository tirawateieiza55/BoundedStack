import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Collections;
import java.util.List;


/** 
 *  ถิรวัฒน์ กอบแก้ว 6821651213 Sec 800
 *  วีรภาพ แซ่จิว 6821651761 Sec 800
*/

public class Officer {

    private static final int Length_ID = 5;
    private final int maxCapacity;

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

    private final List<Integer> ID;

    private void checkRep() {

        assert ID != null  : "list_ID ห้ามเป็น null" ;
        Set<Integer> seen = new HashSet<>();
        for (Integer s : ID) {
            assert s != null   : "สมาชิกห้ามเป็น null" ;
            assert ValidID(s)  : "ID ต้องมี " + Length_ID + " หลัก: " + s;
            assert seen.add(s) : "เลข ID ห้ามซํ้า: " + s;
        }
    }

    /**
     * เช็คความถูกต้องของ ID
     * @param n ต้องเป็นจำนวนเต็มไม่ติดลบ และมีจำนวนหลักเท่ากับ Length_ID พอดี
     * @return true ถ้า n ไม่เป็น null ,  false เป็น null
     */
    private static boolean ValidID(Integer n) {
        if (n == null)   return false;
        return n >= 0 && String.valueOf(n).length() == Length_ID;
    }

    // ===== Creater =====

    // /**
    //  * สร้าง List ID ว่าง
    //  */
    // public Officer() {
    //     this.ID = new Stack<>();
    //     checkRep();
    // }


    //Creater 
    /**
     * รับความจุตอนสร้าง
     * @param capacity ต้องไม่ <= 0 
     * @throws IllegalArgumentException ถ้า capacity <= 0
     */
    public Officer(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity ต้องมากกว่า 0");
        this.maxCapacity = capacity;
        this.ID = new ArrayList<>();
        checkRep();
    }

    /**
     * คืนจำนวน ID ใน List
     */
    public int size() {
        return ID.size();
    }

    /**
     * ตรวจว่ามี ID นี้อยู่หรือไม่
     */

    public boolean contains(Integer n) {
        return this.ID.contains(n);
    }

    /**
     * คืน ID ทั้งหมดตามลำดับ
     */
    public List<Integer> ID() {
        Stack<Integer> copy = new Stack<>();
        copy.addAll(this.ID);
        return copy;
    }

    /**
     * เพิ่ม ID ต่อท้าย
     * @param n ID ต้องไม่เป็น null และไม่ซ้ำ และต้องมี 5 หลัก
     * @return true ถ้าสำเร็จ, false ถ้ามี ID นี้อยู่แล้ว หรือเกิน 5 หลัก หรือ ซ้ำ
     * @throws IllegalArgumentException ถ้า n เป็น null หรือ เกิน 5 หลัก 
     */
    public void push(Integer n) {
        if (n == null) throw new IllegalArgumentException("ห้ามใส่ null");
        if (n < 0 || String.valueOf(n).length() != Length_ID) {
            throw new IllegalArgumentException("ID ต้องมี " + Length_ID + " หลัก: " + n);
        }

        for (Integer i : this.ID) {
            if (i.equals(n)) {
                throw new IllegalArgumentException("ID ซ้ำ: " + n);
            }
        }
        if (this.ID.size() >= maxCapacity) {
            throw new IllegalStateException("Stack เต็มแล้ว");
        }
        this.ID.add(n);
        checkRep();
    }

    /**
     * นำ ID ตัวสุดท้าย ออกจาก Stack
     * @throws IllegalStateException ถ้า Stack ว่าง
     * @return ID ที่ถูกนำออกจาก Stack
     */
    public Integer pop() {
        if (this.ID.isEmpty()) {
            throw new IllegalStateException("Stack ว่าง");
        }
        
        Integer topItem = this.ID.remove(this.ID.size() - 1); //ตัวสุดท้าย
        checkRep();
        return topItem;
    }

    /**
     * คืนสแตกตัวใหม่ที่มีสมาชิกเรียงลำดับจากน้อยไปมาก (โดยไม่แก้ไขสแตกเดิม)
     * @return Officer ตัวใหม่ที่ข้อมูลเรียงลำดับแล้ว
     */
    public Officer sort() {
        List<Integer> sortedList = new ArrayList<>(this.ID);
        Collections.sort(sortedList);

        Officer sortedStack = new Officer(this.maxCapacity);
        for (Integer n : sortedList) {//Push ลงในสแตกตัวใหม่
            sortedStack.push(n);
        }
        return sortedStack;
    }
    
 
    @Override
    public String toString() {
        return ID.toString();
    }
}
