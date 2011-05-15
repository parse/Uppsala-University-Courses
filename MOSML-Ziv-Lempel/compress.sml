(*
Ziv-Lempel compression/decompression in MOSML
Authors: Anders Hassis and Daniel Lervik
*)

load "Polyhash";
load "Array";

(*
    compress (l)
    TYPE: char list -> int list
    PRE: l can only contain characters of the ASCII-table
    POST: Returns l compressed with the Ziv-Lempel technique, where each element returned is one Ziv-Lempel code. The maximum number of codes is 4096, after which no new codes are created and only existing codes are used to compress the remaining characters.
    EXAMPLES: compress(explode("XYZXYXSXS")) = [88, 89, 90, 256, 88, 83, 260];
*)
fun compress (l) = 
    let        
        (* The hashtable that serves as dictionary *)
        val hashLib = Polyhash.mkTable( Polyhash.hash, op = )( 4096, Domain ); 

        (*  
            initLib(i)
            TYPE: int -> unit
            PRE: hashLib is a valid hashtable of structure Polyhash
            POST: ()
            SIDE-EFFECTS: Fills hashLib from key chr(i) to chr(0) with the data i to 0
            VARIANT: i
        *)
        fun initLib(0) = ()
          | initLib(i:int) = 
                (
                    Polyhash.insert hashLib (str(chr(i)) , i) ; 
                    initLib(i-1)
                );

        (*
            compressAux(l, p, numItems)
            TYPE: char list * string * int -> int list
            PRE: hashLib is a valid hashtable of structure Polyhash with 4096 slots
                 l is either [] or contains only elements of ASCII characters #0 to #255
                 (Polyhash.peek hashLib p) <> NONE
            POST: Returns l, where l is the Ziv-Lempel equivalent of p^(the first char of l), where each Ziv-Lempel code is an element in l. The maximum number of codes is 4096, after which no new codes are created and only existing codes are used to compress the remaining characters.
            SIDE-EFFECTS: If not p^(the first char of l)  is a key of hashLib, create a new key p^(the first char of l) and assign the data for the key to numItems+1
            VARIANT: length(l)
        *)
        fun compressAux([], p, _) = [valOf(Polyhash.peek hashLib p)]
          | compressAux(first::rest, p, numItems) = 
            let
                val current = str(first);
                val concat = p^current;
            in
                if (numItems >= 4096) then
                    valOf(Polyhash.peek hashLib p) :: compressAux(rest, current, numItems)
                else if (Polyhash.peek hashLib concat = NONE) then
                    (
                        Polyhash.insert hashLib (concat, numItems + 1);
                        valOf(Polyhash.peek hashLib p) :: compressAux(rest, current, numItems + 1)
                    )
                else
                    compressAux(rest, concat, numItems)
            end;
    in
        (initLib(255) ; compressAux(l, "", 255))
    end;


(*
    decompress (l)
    TYPE: int list -> char list
    PRE: l must be a valid Ziv-Lempel compressed list, coded from a dictionary with a maximum of 4096 number of codes (0-4095)
    POST: Returns each element in l decompressed with the Ziv-Lempel technique
*)
fun decompress (l) =
     let        
        (* The array that serves as dictionary *)
        val arrayLib = Array.array(4096,"");

        (*  
            initLib(i)
            TYPE: int -> unit
            PRE: arrayLib is a valid array of size 256 (0-255).
                 i <= 255
            POST: ()
            SIDE-EFFECTS: Fills arrayLib from index i to 0 with chr(i) to chr(0) as data
            VARIANT: i
        *)
        fun initLib(0) = ()
          | initLib(i:int) = 
                (
                    Array.update(arrayLib, i, str(chr(i)));
                    initLib(i-1)
                );

        (*
            decompressAux(l, numItems)
            TYPE: int list * int -> char list
            PRE: l must be a valid Ziv-Lempel compressed list, coded from a dictionary with a maximum of 4096 number of codes (0-4095)
            POST: Returns each element in l decompressed with the Ziv-Lempel technique
            SIDE-EFFECTS: If numItems < 4095 and if l contains at least two elements; arrayLib is updated at index numItems+1 with f^s if size(s)>0, else f^(the first character of f), where
                          f = (the first element of l):s value in arrayLib
                          s = the first character of (the second element of l):s value in arrayLib
            VARIANT: length l
        *)
        fun decompressAux([], _) = []
          | decompressAux(first::[], _) = explode(Array.sub(arrayLib, first))
          | decompressAux(first::second::rest, numItems) = 
            let
                val firstElmt = Array.sub(arrayLib, first);
                val subSecond = Array.sub(arrayLib, second);

                (* Create new pattern if subSecond = "" *)
                val secondElmt = if (subSecond = "") then
                                    firstElmt ^ substring(firstElmt, 0, 1)
                                 else
                                    subSecond;
            in
                if (numItems >= 4095) then
                    explode(firstElmt) @ decompressAux(second::rest, numItems)
                else
                    (Array.update(arrayLib, numItems + 1, firstElmt ^ substring(secondElmt, 0, 1));
                    explode(firstElmt) @ decompressAux(second::rest, numItems + 1))
            end;
    in
        ( initLib(255) ; decompressAux(l, 255))
    end;    
