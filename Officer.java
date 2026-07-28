import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.List;



/** 
 *  ถิรวัฒน์ กอบแก้ว 6821651213 Sec 800
 *  วีรภาพ แซ่จิว 6821651761 Sec 800
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
            assert ValidID(s) : "ID ต้องมี " + Length_ID + " หลัก: " + s;
            assert seen.add(s) : "เลข ID ห้ามซํ้า: " + s;
        }

    }
    /**
     * เช็คความถูกต้องของ ID
     * @param n ต้องเป็นจำนวนเต็มไม่ติดลบ และมีจำนวนหลักเท่ากับ Length_ID พอดี
     * @return true ถ้า n ไม่เป็น null ,  false เป็น null
     */
    private static boolean ValidID(Integer n) {
        if (n == null) {
            return false;
        }
        return n >= 0 && String.valueOf(n).length() == Length_ID;
    }
    // ===== Creater =====

    /**
     * สร้าง List ID ว่าง
     */
    public Officer() {
        this.ID = new ArrayList<>();
        checkRep();
    }


    //Creater 2
    /**
     * สร้างลิสต์ ID จาก ID 
     * @param initial ID พนักงาน ต้องไม่ซ้ำและไม่เกิน Max_Officer
     * @throws IllegalArgumentException ถ้า initial ผิดเงื่อนไข
     */
    public Officer(List<Integer> initial) {
        if(initial==null) throw new IllegalArgumentException();
        if(initial.size()>Max_Officer) throw new IllegalArgumentException();
        Set<Integer> seen = new HashSet<>();
        for(Integer i : initial){
            if(i == null) throw new IllegalArgumentException();
            if(!ValidID(i)) throw new IllegalArgumentException();
            if(!seen.add(i)) throw new IllegalArgumentException();
            }
        this.ID = new ArrayList<>(initial);
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
        return new ArrayList<>(this.ID);
    }
    /**
     * เพิ่ม ID ต่อท้าย List
     * @param n ID ต้องไม่เป็น null และไม่ซ้ำ และต้องมี 5 หลัก
     * @return true ถ้าสำเร็จ, false ถ้ามี ID นี้อยู่แล้ว หรือเกิน 5 หลัก หรือ ซ้ำ
     * @throws IllegalArgumentException ถ้า n เป็น null หรือ เกิน 5 หลัก 
     */
    public boolean add(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("เลข ID ห้ามเป็น null");
        }
        if (this.ID.contains(n)) {
            return false;
        }
        if (!ValidID(n)) {
            throw new IllegalArgumentException("เลข ID ต้องมี " + Length_ID + " หลัก");
        }
        this.ID.add(n);
        checkRep();
        return true;
    }
    /**
     * ลบ ID ออกจาก List
     *
     * @param n ID ที่ต้องการลบ
     * @throws IllegalArgumentException ถ้า n เป็น null
     * @return true ถ้าลบสำเร็จ, false ถ้าไม่พบ ID นี้
     */
    public boolean remove(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("เลข ID ห้ามเป็น null");
        }
        boolean removed = this.ID.remove(n);
        checkRep();
        return removed;
    }

    /**
     * คืนตัวใหม่ที่มีสมาชิกเรียงลำดับจากน้อยไปมาก
     * @return ID ที่สลับลำดับแล้ว
     */
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
