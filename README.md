
From https://beautifulracket.com/bf/intro.html

When a `bf` program starts, it creates an array of bytes in memory (by convention 30,000 bytes long, each byte initial­ized to 0) and a pointer into that array (initial­ized to the 0 posi­tion). The current byte is the byte in the array at the location indicated by the pointer.

Then it runs the code of the bf program, which consists of six operations:

- `>`  Increase the pointer position by one
- `<`  Decrease the pointer position by one
- `+`  Increase the value of the current byte by one
- `-`  Decrease the value of the current byte by one
- `.`  Write the current byte to stdout
- `,`  Read a byte from stdin and store it in the current byte (overwriting the existing value)

`bf` also has a looping construct `[` `]` that will repeat the code within the brackets until the current byte is zero. If the current byte is already zero, the loop will not run. Loops can be recur­sively nested within other loops.

Any other characters in the source code, including whitespace, are ignored.
