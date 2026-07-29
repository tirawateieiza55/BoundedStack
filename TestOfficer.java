import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestOfficer {

    private static int passed = 0;
    private static int failed = 0;

    
    
    public static void check(String name , boolean condition) {

        if (condition) {
            passed ++ ;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }

    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea OfficerTest\n");
        }

        System.out.println("=== Officer Test ===\n");

        testCreators();
        testPush();
        testPop();
        testObservers();
        testProducer();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }



    public static void testCreators() {
        System.out.println("=== testCreators ===");

        // ต้องสร้างโดยระบุ Capacity
        Officer empty = new Officer(5);
        check("new(5) -> empty", empty.size() == 0);
        check("new(5) -> contains nothing", !empty.contains(1));

        // ทดสอบ Capacity ที่ผิดเงื่อนไข 
        boolean threwZero = false;
        try { 
            new Officer(0); 

        } catch (IllegalArgumentException e) {
            threwZero = true; 
        }
        check("new(0) -> throws IllegalArgumentException", threwZero);

        boolean threwNegative = false;
        try { 
            new Officer(-5); 

        } catch (IllegalArgumentException e) {
            threwNegative = true; 
        }
        check("new(-5) -> throws IllegalArgumentException", threwNegative);
    }

    // --- Mutator: push ต้องรักษาสถานะและกันเลข ID ซ้ำ ---
    private static void testPush() {
        System.out.println("\n=== testPush ===");

        Officer s = new Officer(3);
        s.push(57003);
        check("push(57003) -> size 1", s.size() == 1);
        check("push(57003) -> found by contains", s.contains(57003));

        s.push(76999);
        s.push(87992);
        check("จะต้องมี ID ทั้งหมด 3 ตัวที่ Push เข้ามา", s.ID().equals(Arrays.asList(57003, 76999, 87992)));

        // ทดสอบดัน Stack เกิน Capacity
        boolean threwFull = false;
        try { s.push(99999); 

        } catch (IllegalStateException e) {
            threwFull = true; 
        }
        check("stack เต็ม -> throws IllegalStateException", threwFull);

        // ทดสอบ ID ซ้ำ
        boolean threwDup = false;
        try { s.push(57003); 

        } catch (IllegalArgumentException e) 
        { 
            threwDup = true; 

        }
        check("push ID ที่ซ้ำ -> throws IllegalArgumentException", threwDup);

        // ทดสอบ null และความยาวผิด
        boolean threwNull = false;
        try { s.push(null); 

        } catch (IllegalArgumentException e) {
            threwNull = true; 
        }
        check("push(null) -> throws IllegalArgumentException", threwNull);

        boolean threwShort = false;
        try { s.push(1234); 

        } catch (IllegalArgumentException e)
        { 
            threwShort = true; 

        }
        check("push(4 หลัก) -> throws IllegalArgumentException", threwShort);
    }


    // --- Mutator: pop  ---
    public static void testPop() {
        System.out.println("\n=== testPop ===");

        Officer s = new Officer(5);
        s.push(54204);
        s.push(32444);
        s.push(78543);

        // ทดสอบว่า Pop เอาตัวสุดท้าย (บนสุด) ออกมา
        Integer popped = s.pop();
        check("pop() -> returns 78543", popped.equals(78543));
        check("pop() -> size ต้องเป็น 2", s.size() == 2);
        check("pop() -> ต้องไม่มี ID ที่ลบไปแล้ว", !s.contains(78543));

        s.pop(); 
        s.pop(); 

        // ทดสอบ Pop เมื่อ Stack ว่าง
        boolean threwEmpty = false;
        try { s.pop(); 

        } catch (IllegalStateException e) 
        { 
            threwEmpty = true; 

        }
        check("pop() on empty stack -> throws IllegalStateException", threwEmpty);
    }

    // --- Observer ต้องไม่มี side effect ---
    public static void testObservers() {
        System.out.println("\n=== testObservers ===");

        Officer s = new Officer(5);
        s.push(54204);
        s.push(32444);

        check("size reports 2", s.size() == 2);
        check("ต้องเจอ ID ที่ Push เข้ามา", s.contains(54204));
        check("ต้องไม่เจอ ID ที่ไม่ได้ Push เข้ามา", !s.contains(12345));
        check("ต้องเจอ ID ทั้งหมดที่ Push เข้ามา", s.ID().equals(Arrays.asList(54204, 32444)));

        int before = s.size();
        s.size();
        s.contains(54204);
        s.ID();
        check("การ observe ต้องไม่มี side effects", s.size() == before);
    }

     // --- Producer ต้องคืนตัวใหม่ ไม่แก้ตัวเดิม ---
    public static void testProducer() {
        System.out.println("\n=== testProducer ===");

        Officer original = new Officer(5);
        original.push(54204);
        original.push(32444);
        original.push(78543);

        Officer sorted = original.sort();

        check("sorted has the same size", sorted.size() == original.size());

        List<Integer> expected = Arrays.asList(32444, 54204, 78543);
        check("ตัวที่สร้างมาใหม่ต้องมี ID ที่เรียงลำดับถูกต้อง", sorted.ID().equals(expected));
        
        check("sorted ต้องไม่แก้ original", 
                original.ID().equals(Arrays.asList(54204, 32444, 78543)));

        sorted.push(99999);
        check("ตัวที่สร้างมาใหม่ต้องไม่กระทบ original", original.size() == 3);
    }
    


    
    // --- ทดสอบว่าไม่เกิด representation exposure ---
    public static void testExposure() {
        System.out.println("\n=== testExposure ===");

        Officer s = new Officer(5);
        s.push(54204);

        // ขาออก: แก้ list ที่ได้จาก ID() ต้องไม่กระทบ rep
        List<Integer> got = s.ID();
        got.clear();
        check("การ clear ต้องไม่กระทบ original", s.size() == 1);

        got = s.ID();
        got.add(12345);
        check("การ add ต้องไม่กระทบ original", s.size() == 1 && !s.contains(12345));

        check("ID() ต้องคืน list ใหม่ทุกครั้ง", s.ID() != s.ID());
    }



}