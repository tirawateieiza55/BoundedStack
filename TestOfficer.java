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
        testAdd();
        testRemove();
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

        Officer empty = new Officer();
        check("new() -> empty", empty.size() == 0);
        check("new() -> contains nothing", !empty.contains(1));

        Officer p = new Officer(Arrays.asList(31852, 55555, 42132));
        check("new(list) -> size 3", p.size() == 3);
        check("new(list) -> contains 31852", p.contains(31852));
        check("new(list) -> preserves order",
        p.ID().equals(Arrays.asList(31852, 55555, 42132)));

        Officer fromEmpty = new Officer(new ArrayList<>());
        check("new(empty list) -> empty", fromEmpty.size() == 0);

        // input ที่ผิดเงื่อนไขต้องโยน exception ไม่ใช่ปล่อยผ่าน
        boolean threwDup = false;
        try {
            new Officer(Arrays.asList(3185244, 31852));
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDup);

        boolean threwNull = false;
        try {
            new Officer(Arrays.asList(31852, null));
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("new(list with null) -> throws IllegalArgumentException", threwNull);

        boolean threwNullList = false;
        try {
            new Officer(null);
        } catch (IllegalArgumentException e) {
            threwNullList = true;
        }
        check("new(null) -> throws IllegalArgumentException", threwNullList);

        boolean threw1 = false;
        try {
            new Officer(Arrays.asList(3, 5));
        } catch (IllegalArgumentException e) {
            threw1 = true;
        }
        check("new(ส่งเลขตัวเดียวไม่ถึง 5 หลัก) -> throws IllegalArgumentException", threw1);

        boolean threw2 = false;
        try {
            new Officer(Arrays.asList(5321323));
        } catch (IllegalArgumentException e) {
            threw2 = true;
        }
        check("new(ส่งเลขที่เกิน Length_ID) -> throws IllegalArgumentException", threw2);

    }
    // --- Mutator: add ต้องรักษาสถานะและกันเลข ID ซ้ำ ---
    private static void testAdd() {
        System.out.println("\n-- Add --");

        Officer s = new Officer();
        check("add(57003) -> returns true", s.add(57003));
        check("add(57003) -> size 1", s.size() == 1);
        check("add(57003) -> found by contains", s.contains(57003));

        s.add(76999);
        s.add(87992);
        check("add preserves insertion order",
                s.ID().equals(Arrays.asList(57003, 76999, 87992)));

        // ID ซ้ำไม่ใช่ error — คืน false เฉย ๆ
        check("add duplicate -> returns false", !s.add(57003));
        check("failed add leaves size unchanged", s.size() == 3);

        // input ที่ผิดเงื่อนไขต้องโยน exception
        boolean threwNull = false;
        try {
            s.add(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("add(null) -> throws IllegalArgumentException", threwNull);

        check("failed adds leave Officer unchanged", s.size() == 3);

        boolean threw1 = false;
        try {
            s.add(3);
        } catch (IllegalArgumentException e) {
            threw1 = true;
        }
        check("add(ส่งเลขตัวเดียว) -> throws IllegalArgumentException", threw1);

        boolean threw2 = false;
        try {
            s.add(5321323);
        } catch (IllegalArgumentException e) {
            threw2 = true;
        }
        check("add(ส่งเลขที่เกิน Length) -> throws IllegalArgumentException", threw2);
        

        Officer full = new Officer();
        for (int i = 0; i < Officer.Max_Officer; i++) {
            full.add(10000 + i);
        }
        check("can fill up to Max_Officer", full.size() == Officer.Max_Officer);

    }


    // --- Mutator: remove ทั้งกรณีพบและไม่พบ ---
    public static void testRemove() {
        System.out.println("=== testRemove ===");

        Officer s = new Officer(Arrays.asList(54204, 32444,78543));
        check("remove(78543) -> returns true", s.remove(78543));
        check("remove -> size decreases", s.size() == 2);
        check("remove -> ID is gone", !s.contains(78543));
        check("remove keeps the others in order",
                s.ID().equals(Arrays.asList(54204, 32444)));

         // ลบIDที่ไม่มีไม่ใช่ error — คืน false เฉย ๆ
        check("remove missing ID -> returns false", !s.remove(12345));
        check("failed remove leaves size unchanged", s.size() == 2);

        s.remove(54204);
        s.remove(32444);

        check("remove all -> empty", s.size() == 0);
        check("remove on empty ID -> returns false", !s.remove(54204));
    }

    // --- Observer ต้องไม่มี side effect ---
    public static void testObservers() {
        System.out.println("=== testObservers ===");

        Officer s = new Officer(Arrays.asList(54204, 32444));
        check("size reports 2", s.size() == 2);
        check("contains finds an existing ID", s.contains(54204));
        check("contains rejects a missing ID", !s.contains(12345));
        check("ID returns the full list in order",
                s.ID().equals(Arrays.asList(54204, 32444)));

        int before = s.size();
        s.size();
        s.contains(54204);
        s.ID();
        check("observers have no side effects", s.size() == before);
    }

     // --- Producer ต้องคืนตัวใหม่ ไม่แก้ตัวเดิม ---
    public static void testProducer() {
        System.out.println("=== testProducer ===");

        Officer original = new Officer(Arrays.asList(54204, 32444, 78543));
        Officer sorted = original.sort();

        check("sorted has the same size", sorted.size() == original.size());

        List<Integer> a = new ArrayList<Integer>(original.ID());
        List<Integer> b = new ArrayList<Integer>(sorted.ID());
        Collections.sort(a);
        Collections.sort(b);
        check("sorted contains exactly the same IDs", a.equals(b));

        check("sorted does not mutate the original",
                original.ID().equals(Arrays.asList(54204, 32444, 78543)));

        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        sorted.add(12345);
        check("mutating the result does not affect the original",
                original.size() == 3);

        // boundary: sorted ID ว่างต้องไม่พัง
        Officer emptySorted = new Officer().sort();
        check("sorting an empty officer is safe", emptySorted.size() == 0);

    }
    


    
    // --- ทดสอบว่าไม่เกิด representation exposure ---
    public static void testExposure() {
        System.out.println("=== testExposure ===");


         // ขาออก: แก้ list ที่ได้จาก ID() ต้องไม่กระทบ rep
        Officer s = new Officer();
        s.add(54204);

        List<Integer> got = s.ID();
        got.clear();
        check("clearing result of ID() does not affect officer",
                s.size() == 1);

        got = s.ID();
        got.add(12345);
        check("adding to result of ID() does not affect officer",
                s.size() == 1 && !s.contains(12345));

         // สองครั้งต้องเป็นคนละ object
        check("ID() returns a fresh list each call",
                s.ID() != s.ID());

        // ขาเข้า: แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
        List<Integer> input = new ArrayList<Integer>(Arrays.asList(54204, 32444));
        Officer p = new Officer(input);


        input.clear();
        check("clearing constructor argument does not affect officer",
                p.size() == 2);

        input.add(12345);
        check("adding to constructor argument does not affect officer",
                !p.contains(12345));

    }



}