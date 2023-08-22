public class Vigenere {



    public static String Decrypt(String cipherText, String key) {
        String cipher = cipherText.toLowerCase();
        key = key.toLowerCase();
        char[] key_arr = key.toCharArray();
        char[] cipher_arr = cipher.toCharArray();
        char[] plain_text = new char[cipher.length()];

        // making table
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int index = 0;
        char[][] array = new char[26][26];
        for (int i = 0; i < 26; i++) {
            index = i;
            for (int j = 0; j < 26; j++) {
                if (index >= 26) {
                    index = index % 26;
                }
                array[i][j] = alphabet[index];
                index += 1;
            }
        }
        // table done

        char[] new_key = new char[cipher.length()];
        int x = 0, y = 0;
        int curr_ind = 0;
        int key_pos = key.length();
        int rep_ind = 0;

        // copy key into new key arr
        for (int i = 0; i < key.length(); i++) {
            new_key[i] = key_arr[i];
        }

        // starting of decrypt
        for (int i = 0; i < cipher.length(); i++) {
            for (int j = 0; j < 26; j++) {
                if (array[j][0] == new_key[i]) {
                    x = j;
                    break;
                }
            }
            for (int j = 0; j < 26; j++) {
                if (cipher_arr[i] == array[x][j]) {
                    y = j;
                    break;
                }
            }
            plain_text[i] = array[0][y];

            if (key_pos < cipher.length()) {
                new_key[key_pos] = plain_text[i];
            }

            key_pos++;
        }

        String cipherstr = new String(plain_text);
        return cipherstr;
    }


    public static void main(String[] args) {
        System.out.println(Decrypt("zicvtwqngkzeiigasxstslvvwla" , "deceptive"));
    }






}
