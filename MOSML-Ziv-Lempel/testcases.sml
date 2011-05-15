(* 
Training cases for Ziv Lempel datacompression
by Sven-Olof Nyström.

This file contains five simple test cases for data compression. For
more complex test cases, see separate files. Each test case is
presented as two val declarations, for example:

val xyz = "XYZ"
val xyzC = [88, 89, 90]

To check that your compression produces correct result for this input,
try:

xyzC = compress(explode xyz);

To check that your decompression produces correct result for the given
compressed result, try:

xyz = implode (decompress xyzC);

*)

(* Example 1. No compression. *)

val xyz = "XYZ"
val xyzC = [88, 89, 90]

(* Example 2. One substring is comressed. Try

xyzxyC = compress(explode xyzxy)
xyzxy = implode (decompress xyzxyC)

*)

val xyzxy = "XYZXY"
val xyzxyC = [88, 89, 90, 256]

(* Example 3. The tricky case where a new string in the dictionary is
   used immediately. Try

threeC = compress(explode three)
three = implode(decompress threeC)

*)

val three = "xxx"
val threeC = [120, 256] 

(* Example 4. A string with new lines. Try print shortString to see
   what it looks like when output. To test, try

shortStringC = compress(explode shortString)
shortString = implode(decompress shortStringC)

*)

val shortString = "A line\nand another\n"
val shortStringC =
    [65, 32, 108, 105, 110, 101, 10, 97, 110, 100, 32, 263, 111, 116, 104, 101,
     114, 10]

(* Example 5. A longer string. 

   Hint: When compressing, set

   Meta.printLength := 400; 

   to view the whole output.

   To test, try

longStringC = compress(explode longString)
longString = implode(decompress longStringC)

*)

val longString = "The main objective is to make you familiar with some\n"^
"fundamental principles and methodologies of algorithm and\n"^
"data-structure design and evaluation. We will use the functional\n"^
"programming language Standard ML (SML) as the teaching medium. You\n"^
"will learn when to use each discussed algorithm and data structure,\n"^
"when not to, and what to consider when designing or choosing an\n"^
"algorithm or data structure. The lectures are in English, by the\n"^
"instructor, and have a slight theoretical flavour, but with many\n"^
"examples.";

val longStringC =
    [84, 104, 101, 32, 109, 97, 105, 110, 32, 111, 98, 106, 101, 99, 116, 105,
     118, 258, 105, 115, 32, 116, 111, 259, 97, 107, 258, 121, 111, 117, 32,
     102, 97, 109, 105, 108, 105, 97, 114, 32, 119, 105, 116, 104, 32, 115,
     111, 109, 101, 10, 102, 117, 110, 100, 288, 101, 110, 116, 97, 108, 32,
     112, 114, 262, 99, 105, 112, 108, 101, 275, 97, 308, 259, 101, 298, 111,
     100, 111, 108, 111, 103, 105, 324, 264, 102, 32, 314, 103, 111, 318, 298,
     109, 341, 308, 10, 309, 313, 45, 115, 116, 114, 117, 269, 117, 114, 258,
     100, 324, 105, 103, 263, 326, 100, 32, 101, 118, 314, 117, 97, 270, 111,
     110, 46, 32, 87, 258, 296, 108, 315, 117, 115, 258, 298, 258, 306, 110,
     269, 105, 376, 314, 10, 317, 335, 114, 288, 289, 110, 103, 32, 108, 326,
     103, 373, 103, 258, 83, 313, 308, 293, 368, 77, 76, 32, 40, 83, 416, 41,
     341, 275, 388, 276, 101, 97, 99, 104, 262, 403, 303, 100, 105, 117, 109,
     378, 89, 284, 10, 382, 315, 323, 293, 263, 119, 257, 263, 277, 32, 385,
     258, 427, 429, 32, 434, 115, 99, 385, 386, 368, 342, 344, 297, 104, 347,
     367, 456, 374, 97, 300, 355, 357, 116, 359, 101, 44, 441, 448, 32, 110,
     111, 116, 276, 111, 44, 348, 368, 447, 374, 485, 32, 99, 376, 115, 105,
     362, 294, 447, 311, 456, 363, 365, 431, 264, 294, 429, 111, 111, 496, 402,
     348, 10, 463, 345, 466, 506, 469, 313, 472, 356, 358, 360, 378, 256, 258,
     323, 523, 338, 293, 273, 263, 69, 402, 291, 115, 104, 487, 98, 121, 276,
     257, 10, 262, 354, 522, 277, 114, 487, 468, 104, 97, 272, 341, 300, 291,
     103, 104, 484, 388, 344, 329, 105, 99, 314, 286, 405, 118, 284, 549, 32,
     98, 117, 484, 296, 298, 279, 110, 121, 10, 101, 120, 288, 322, 324, 46];

val xyz = "XYZ";
val xyzC = [88, 89, 90];

xyzC = compress(explode xyz);
xyz = implode (decompress xyzC);

threeC = compress(explode three);
three = implode(decompress threeC);

shortStringC = compress(explode shortString);
shortString = implode(decompress shortStringC);

longStringC = compress(explode longString);
longString = implode(decompress longStringC);
