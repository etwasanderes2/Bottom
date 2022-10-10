# Bottom: A secure-ish password generator
⚠️ **DO NOT USE THIS APP FOR ANYTHING IMPORTNAT!** ⚠️

Uses Java's `SecureRandom` to generate passwords and encodes them in various formats.  
**There are probably things you need to do to android apps to make them handle passwords safely. I did not do those things!**  
Also storing passwords in the clipboard is probably bad practice  

## Features
- Generate random bytes and encode them as
  - Base 64 without padding
  - Base 64 with padding (`=`)
  - Hexadecimal
- Copy the result to clipboard with one easy button push
