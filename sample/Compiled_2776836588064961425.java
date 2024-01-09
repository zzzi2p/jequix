package net.i2p.pow.hashx;
public class Compiled_2776836588064961425 {
public static void execute(long[] r) {
long r0 = r[0];
long r1 = r[1];
long r2 = r[2];
long r3 = r[3];
long r4 = r[4];
long r5 = r[5];
long r6 = r[6];
long r7 = r[7];
boolean branch_enable = true;
int result = 0;
// 0: (2) INSTR_MUL_R r5-> r0 op_par 5
r0 *= r5;
// 1: (9) INSTR_TARGET imm 0
// branch target 1
for (int x = 0; x < 2; x++) {
// 2: (2) INSTR_MUL_R r3-> r7 op_par 3
r7 *= r3;
// 3: (7) INSTR_ADD_C r4 imm 918998126
r4 += 918998126;
// 4: (8) INSTR_XOR_C r3 imm -698078947
r3 ^= -698078947;
// 5: (2) INSTR_MUL_R r3-> r4 op_par 3
r4 *= r3;
// 6: (8) INSTR_XOR_C r5 imm 583734308
r5 ^= 583734308;
// 7: (4) INSTR_XOR_R r6-> r2 op_par 6
r2 ^= r6;
// 8: (2) INSTR_MUL_R r6-> r5 op_par 6
r5 *= r6;
// 9: (8) INSTR_XOR_C r2 imm -1282186302
r2 ^= -1282186302;
// 10: (5) INSTR_ADD_RS r1-> r6 op_par 1
r6 += r1 << 0;
// 11: (1) INSTR_SMULH_R r7-> r0 op_par 2027297024
r0 = Exec.smulh(r0, r7);
result = (int) r0;
// 12: (8) INSTR_XOR_C r6 imm 1206547696
r6 ^= 1206547696;
// 13: (2) INSTR_MUL_R r1-> r6 op_par 1
r6 *= r1;
// 14: (4) INSTR_XOR_R r7-> r4 op_par 7
r4 ^= r7;
// 15: (8) INSTR_XOR_C r1 imm -265024627
r1 ^= -265024627;
// 16: (2) INSTR_MUL_R r1-> r3 op_par 1
r3 *= r1;
// 17: (10) INSTR_BRANCH imm 540737
if (x == 0 && branch_enable && ((result & 540737) == 0)) {
// branch to target from 17
    branch_enable = false;
} else {
    break;
}
} // for loop
// 18: (2) INSTR_MUL_R r1-> r4 op_par 1
r4 *= r1;
// 19: (5) INSTR_ADD_RS r2-> r1 op_par 2
r1 += r2 << 0;
// 20: (6) INSTR_ROR_C r7 imm 62
r7 = Long.rotateRight(r7, 62);
// 21: (1) INSTR_SMULH_R r1-> r5 op_par 352713360
r5 = Exec.smulh(r5, r1);
result = (int) r5;
// 22: (6) INSTR_ROR_C r2 imm 19
r2 = Long.rotateRight(r2, 19);
// 23: (2) INSTR_MUL_R r6-> r2 op_par 6
r2 *= r6;
// 24: (5) INSTR_ADD_RS r1-> r3 op_par 1
r3 += r1 << 3;
// 25: (6) INSTR_ROR_C r1 imm 43
r1 = Long.rotateRight(r1, 43);
// 26: (2) INSTR_MUL_R r4-> r7 op_par 4
r7 *= r4;
// 27: (4) INSTR_XOR_R r6-> r1 op_par 6
r1 ^= r6;
// 28: (8) INSTR_XOR_C r3 imm -1240560327
r3 ^= -1240560327;
// 29: (2) INSTR_MUL_R r4-> r3 op_par 4
r3 *= r4;
// 30: (8) INSTR_XOR_C r0 imm -1182554095
r0 ^= -1182554095;
// 31: (7) INSTR_ADD_C r4 imm 1433633916
r4 += 1433633916;
// 32: (2) INSTR_MUL_R r6-> r4 op_par 6
r4 *= r6;
// 33: (9) INSTR_TARGET imm 0
// branch target 33
for (int x = 0; x < 2; x++) {
// 34: (2) INSTR_MUL_R r2-> r0 op_par 2
r0 *= r2;
// 35: (8) INSTR_XOR_C r2 imm -1042526724
r2 ^= -1042526724;
// 36: (5) INSTR_ADD_RS r1-> r6 op_par 1
r6 += r1 << 0;
// 37: (2) INSTR_MUL_R r5-> r1 op_par 5
r1 *= r5;
// 38: (3) INSTR_SUB_R r7-> r5 op_par 7
r5 -= r7;
// 39: (7) INSTR_ADD_C r7 imm -1826516400
r7 += -1826516400;
// 40: (2) INSTR_MUL_R r6-> r5 op_par 6
r5 *= r6;
// 41: (8) INSTR_XOR_C r6 imm -878315069
r6 ^= -878315069;
// 42: (7) INSTR_ADD_C r4 imm 565290182
r4 += 565290182;
// 43: (1) INSTR_SMULH_R r2-> r4 op_par 1026064479
r4 = Exec.smulh(r4, r2);
result = (int) r4;
// 44: (4) INSTR_XOR_R r7-> r3 op_par 7
r3 ^= r7;
// 45: (2) INSTR_MUL_R r3-> r7 op_par 3
r7 *= r3;
// 46: (8) INSTR_XOR_C r1 imm 487275900
r1 ^= 487275900;
// 47: (7) INSTR_ADD_C r0 imm -1069855930
r0 += -1069855930;
// 48: (2) INSTR_MUL_R r2-> r1 op_par 2
r1 *= r2;
// 49: (10) INSTR_BRANCH imm -2079849472
if (x == 0 && branch_enable && ((result & -2079849472) == 0)) {
// branch to target from 49
    branch_enable = false;
} else {
    break;
}
} // for loop
// 50: (2) INSTR_MUL_R r5-> r6 op_par 5
r6 *= r5;
// 51: (7) INSTR_ADD_C r5 imm -363774605
r5 += -363774605;
// 52: (3) INSTR_SUB_R r3-> r0 op_par 3
r0 -= r3;
// 53: (0) INSTR_UMULH_R r5-> r2 op_par -1214315108
r2 = Exec.umulh(r2, r5);
result = (int) r2;
// 54: (8) INSTR_XOR_C r5 imm 1366494073
r5 ^= 1366494073;
// 55: (2) INSTR_MUL_R r5-> r4 op_par 5
r4 *= r5;
// 56: (6) INSTR_ROR_C r0 imm 4
r0 = Long.rotateRight(r0, 4);
// 57: (7) INSTR_ADD_C r3 imm -1802783690
r3 += -1802783690;
// 58: (2) INSTR_MUL_R r7-> r0 op_par 7
r0 *= r7;
// 59: (8) INSTR_XOR_C r7 imm -1571469233
r7 ^= -1571469233;
// 60: (7) INSTR_ADD_C r1 imm 1270710890
r1 += 1270710890;
// 61: (2) INSTR_MUL_R r7-> r3 op_par 7
r3 *= r7;
// 62: (8) INSTR_XOR_C r1 imm -1091600555
r1 ^= -1091600555;
// 63: (5) INSTR_ADD_RS r6-> r7 op_par 6
r7 += r6 << 2;
// 64: (2) INSTR_MUL_R r5-> r2 op_par 5
r2 *= r5;
// 65: (9) INSTR_TARGET imm 0
// branch target 65
for (int x = 0; x < 2; x++) {
// 66: (2) INSTR_MUL_R r1-> r5 op_par 1
r5 *= r1;
// 67: (3) INSTR_SUB_R r1-> r0 op_par 1
r0 -= r1;
// 68: (6) INSTR_ROR_C r7 imm 1
r7 = Long.rotateRight(r7, 1);
// 69: (2) INSTR_MUL_R r6-> r1 op_par 6
r1 *= r6;
// 70: (8) INSTR_XOR_C r4 imm -821741886
r4 ^= -821741886;
// 71: (4) INSTR_XOR_R r7-> r3 op_par 7
r3 ^= r7;
// 72: (2) INSTR_MUL_R r2-> r4 op_par 2
r4 *= r2;
// 73: (8) INSTR_XOR_C r2 imm -588419524
r2 ^= -588419524;
// 74: (7) INSTR_ADD_C r6 imm 1105215811
r6 += 1105215811;
// 75: (0) INSTR_UMULH_R r7-> r6 op_par -244521778
r6 = Exec.umulh(r6, r7);
result = (int) r6;
// 76: (4) INSTR_XOR_R r3-> r5 op_par 3
r5 ^= r3;
// 77: (2) INSTR_MUL_R r7-> r3 op_par 7
r3 *= r7;
// 78: (6) INSTR_ROR_C r0 imm 7
r0 = Long.rotateRight(r0, 7);
// 79: (3) INSTR_SUB_R r2-> r5 op_par 2
r5 -= r2;
// 80: (2) INSTR_MUL_R r5-> r7 op_par 5
r7 *= r5;
// 81: (10) INSTR_BRANCH imm 671220736
if (x == 0 && branch_enable && ((result & 671220736) == 0)) {
// branch to target from 81
    branch_enable = false;
} else {
    break;
}
} // for loop
// 82: (2) INSTR_MUL_R r2-> r0 op_par 2
r0 *= r2;
// 83: (3) INSTR_SUB_R r4-> r1 op_par 4
r1 -= r4;
// 84: (4) INSTR_XOR_R r5-> r2 op_par 5
r2 ^= r5;
// 85: (1) INSTR_SMULH_R r5-> r6 op_par 1081272109
r6 = Exec.smulh(r6, r5);
result = (int) r6;
// 86: (8) INSTR_XOR_C r4 imm -623979936
r4 ^= -623979936;
// 87: (2) INSTR_MUL_R r5-> r4 op_par 5
r4 *= r5;
// 88: (7) INSTR_ADD_C r5 imm 626048063
r5 += 626048063;
// 89: (6) INSTR_ROR_C r3 imm 58
r3 = Long.rotateRight(r3, 58);
// 90: (2) INSTR_MUL_R r2-> r5 op_par 2
r5 *= r2;
// 91: (4) INSTR_XOR_R r2-> r0 op_par 2
r0 ^= r2;
// 92: (7) INSTR_ADD_C r3 imm 1012376755
r3 += 1012376755;
// 93: (2) INSTR_MUL_R r7-> r3 op_par 7
r3 *= r7;
// 94: (3) INSTR_SUB_R r0-> r7 op_par 0
r7 -= r0;
// 95: (7) INSTR_ADD_C r0 imm -357634660
r0 += -357634660;
// 96: (2) INSTR_MUL_R r0-> r6 op_par 0
r6 *= r0;
// 97: (9) INSTR_TARGET imm 0
// branch target 97
for (int x = 0; x < 2; x++) {
// 98: (2) INSTR_MUL_R r7-> r0 op_par 7
r0 *= r7;
// 99: (7) INSTR_ADD_C r4 imm 2029441129
r4 += 2029441129;
// 100: (4) INSTR_XOR_R r5-> r7 op_par 5
r7 ^= r5;
// 101: (2) INSTR_MUL_R r7-> r1 op_par 7
r1 *= r7;
// 102: (7) INSTR_ADD_C r5 imm 2017505041
r5 += 2017505041;
// 103: (4) INSTR_XOR_R r3-> r4 op_par 3
r4 ^= r3;
// 104: (2) INSTR_MUL_R r7-> r4 op_par 7
r4 *= r7;
// 105: (8) INSTR_XOR_C r2 imm 865652258
r2 ^= 865652258;
// 106: (6) INSTR_ROR_C r5 imm 60
r5 = Long.rotateRight(r5, 60);
// 107: (0) INSTR_UMULH_R r3-> r0 op_par 881520140
r0 = Exec.umulh(r0, r3);
result = (int) r0;
// 108: (6) INSTR_ROR_C r2 imm 5
r2 = Long.rotateRight(r2, 5);
// 109: (2) INSTR_MUL_R r1-> r7 op_par 1
r7 *= r1;
// 110: (4) INSTR_XOR_R r3-> r5 op_par 3
r5 ^= r3;
// 111: (6) INSTR_ROR_C r6 imm 31
r6 = Long.rotateRight(r6, 31);
// 112: (2) INSTR_MUL_R r2-> r5 op_par 2
r5 *= r2;
// 113: (10) INSTR_BRANCH imm 4148
if (x == 0 && branch_enable && ((result & 4148) == 0)) {
// branch to target from 113
    branch_enable = false;
} else {
    break;
}
} // for loop
// 114: (2) INSTR_MUL_R r3-> r2 op_par 3
r2 *= r3;
// 115: (8) INSTR_XOR_C r3 imm 1391591219
r3 ^= 1391591219;
// 116: (5) INSTR_ADD_RS r6-> r4 op_par 6
r4 += r6 << 3;
// 117: (0) INSTR_UMULH_R r4-> r4 op_par -1106872820
r4 = Exec.umulh(r4, r4);
result = (int) r4;
// 118: (3) INSTR_SUB_R r7-> r3 op_par 7
r3 -= r7;
// 119: (2) INSTR_MUL_R r0-> r6 op_par 0
r6 *= r0;
// 120: (6) INSTR_ROR_C r3 imm 34
r3 = Long.rotateRight(r3, 34);
// 121: (5) INSTR_ADD_RS r1-> r0 op_par 1
r0 += r1 << 2;
// 122: (2) INSTR_MUL_R r1-> r0 op_par 1
r0 *= r1;
// 123: (3) INSTR_SUB_R r5-> r1 op_par 5
r1 -= r5;
// 124: (6) INSTR_ROR_C r2 imm 13
r2 = Long.rotateRight(r2, 13);
// 125: (2) INSTR_MUL_R r2-> r1 op_par 2
r1 *= r2;
// 126: (3) INSTR_SUB_R r7-> r3 op_par 7
r3 -= r7;
// 127: (4) INSTR_XOR_R r7-> r2 op_par 7
r2 ^= r7;
// 128: (2) INSTR_MUL_R r7-> r3 op_par 7
r3 *= r7;
// 129: (9) INSTR_TARGET imm 0
// branch target 129
for (int x = 0; x < 2; x++) {
// 130: (2) INSTR_MUL_R r7-> r2 op_par 7
r2 *= r7;
// 131: (7) INSTR_ADD_C r5 imm 1926588407
r5 += 1926588407;
// 132: (8) INSTR_XOR_C r7 imm -102267399
r7 ^= -102267399;
// 133: (2) INSTR_MUL_R r6-> r7 op_par 6
r7 *= r6;
// 134: (7) INSTR_ADD_C r0 imm -1669854175
r0 += -1669854175;
// 135: (3) INSTR_SUB_R r4-> r5 op_par 4
r5 -= r4;
// 136: (2) INSTR_MUL_R r5-> r0 op_par 5
r0 *= r5;
// 137: (8) INSTR_XOR_C r1 imm -598161192
r1 ^= -598161192;
// 138: (7) INSTR_ADD_C r6 imm -362644075
r6 += -362644075;
// 139: (1) INSTR_SMULH_R r4-> r4 op_par -276732613
r4 = Exec.smulh(r4, r4);
result = (int) r4;
// 140: (8) INSTR_XOR_C r6 imm 1355853884
r6 ^= 1355853884;
// 141: (2) INSTR_MUL_R r3-> r5 op_par 3
r5 *= r3;
// 142: (7) INSTR_ADD_C r6 imm -59875819
r6 += -59875819;
// 143: (3) INSTR_SUB_R r2-> r3 op_par 2
r3 -= r2;
// 144: (2) INSTR_MUL_R r2-> r1 op_par 2
r1 *= r2;
// 145: (10) INSTR_BRANCH imm 4208640
if (x == 0 && branch_enable && ((result & 4208640) == 0)) {
// branch to target from 145
    branch_enable = false;
} else {
    break;
}
} // for loop
// 146: (2) INSTR_MUL_R r2-> r6 op_par 2
r6 *= r2;
// 147: (8) INSTR_XOR_C r0 imm -599577562
r0 ^= -599577562;
// 148: (6) INSTR_ROR_C r2 imm 42
r2 = Long.rotateRight(r2, 42);
// 149: (1) INSTR_SMULH_R r3-> r7 op_par -824426036
r7 = Exec.smulh(r7, r3);
result = (int) r7;
// 150: (6) INSTR_ROR_C r3 imm 46
r3 = Long.rotateRight(r3, 46);
// 151: (2) INSTR_MUL_R r1-> r4 op_par 1
r4 *= r1;
// 152: (7) INSTR_ADD_C r1 imm -898757622
r1 += -898757622;
// 153: (8) INSTR_XOR_C r2 imm -93430622
r2 ^= -93430622;
// 154: (2) INSTR_MUL_R r5-> r2 op_par 5
r2 *= r5;
// 155: (8) INSTR_XOR_C r1 imm 1443260367
r1 ^= 1443260367;
// 156: (3) INSTR_SUB_R r6-> r3 op_par 6
r3 -= r6;
// 157: (2) INSTR_MUL_R r1-> r0 op_par 1
r0 *= r1;
// 158: (8) INSTR_XOR_C r5 imm 1339533603
r5 ^= 1339533603;
// 159: (7) INSTR_ADD_C r6 imm 529049930
r6 += 529049930;
// 160: (2) INSTR_MUL_R r6-> r5 op_par 6
r5 *= r6;
// 161: (9) INSTR_TARGET imm 0
// branch target 161
for (int x = 0; x < 2; x++) {
// 162: (2) INSTR_MUL_R r3-> r7 op_par 3
r7 *= r3;
// 163: (6) INSTR_ROR_C r1 imm 46
r1 = Long.rotateRight(r1, 46);
// 164: (3) INSTR_SUB_R r4-> r2 op_par 4
r2 -= r4;
// 165: (2) INSTR_MUL_R r1-> r3 op_par 1
r3 *= r1;
// 166: (3) INSTR_SUB_R r1-> r4 op_par 1
r4 -= r1;
// 167: (7) INSTR_ADD_C r1 imm -1784621160
r1 += -1784621160;
// 168: (2) INSTR_MUL_R r4-> r6 op_par 4
r6 *= r4;
// 169: (5) INSTR_ADD_RS r4-> r1 op_par 4
r1 += r4 << 1;
// 170: (6) INSTR_ROR_C r2 imm 26
r2 = Long.rotateRight(r2, 26);
// 171: (0) INSTR_UMULH_R r7-> r2 op_par 937338351
r2 = Exec.umulh(r2, r7);
result = (int) r2;
// 172: (6) INSTR_ROR_C r4 imm 14
r4 = Long.rotateRight(r4, 14);
// 173: (2) INSTR_MUL_R r7-> r4 op_par 7
r4 *= r7;
// 174: (3) INSTR_SUB_R r7-> r1 op_par 7
r1 -= r7;
// 175: (8) INSTR_XOR_C r7 imm -1212085189
r7 ^= -1212085189;
// 176: (2) INSTR_MUL_R r5-> r7 op_par 5
r7 *= r5;
// 177: (10) INSTR_BRANCH imm -2109732864
if (x == 0 && branch_enable && ((result & -2109732864) == 0)) {
// branch to target from 177
    branch_enable = false;
} else {
    break;
}
} // for loop
// 178: (2) INSTR_MUL_R r3-> r1 op_par 3
r1 *= r3;
// 179: (4) INSTR_XOR_R r3-> r5 op_par 3
r5 ^= r3;
// 180: (7) INSTR_ADD_C r6 imm 1036470567
r6 += 1036470567;
// 181: (0) INSTR_UMULH_R r0-> r2 op_par -1210450611
r2 = Exec.umulh(r2, r0);
result = (int) r2;
// 182: (8) INSTR_XOR_C r4 imm -712845317
r4 ^= -712845317;
// 183: (2) INSTR_MUL_R r6-> r4 op_par 6
r4 *= r6;
// 184: (7) INSTR_ADD_C r7 imm 1447408337
r7 += 1447408337;
// 185: (4) INSTR_XOR_R r6-> r3 op_par 6
r3 ^= r6;
// 186: (2) INSTR_MUL_R r0-> r5 op_par 0
r5 *= r0;
// 187: (8) INSTR_XOR_C r7 imm 937394791
r7 ^= 937394791;
// 188: (6) INSTR_ROR_C r0 imm 22
r0 = Long.rotateRight(r0, 22);
// 189: (2) INSTR_MUL_R r0-> r6 op_par 0
r6 *= r0;
// 190: (3) INSTR_SUB_R r3-> r1 op_par 3
r1 -= r3;
// 191: (6) INSTR_ROR_C r3 imm 17
r3 = Long.rotateRight(r3, 17);
// 192: (2) INSTR_MUL_R r7-> r0 op_par 7
r0 *= r7;
// 193: (9) INSTR_TARGET imm 0
// branch target 193
for (int x = 0; x < 2; x++) {
// 194: (2) INSTR_MUL_R r2-> r7 op_par 2
r7 *= r2;
// 195: (6) INSTR_ROR_C r1 imm 30
r1 = Long.rotateRight(r1, 30);
// 196: (7) INSTR_ADD_C r4 imm -788477042
r4 += -788477042;
// 197: (2) INSTR_MUL_R r4-> r1 op_par 4
r1 *= r4;
// 198: (5) INSTR_ADD_RS r3-> r2 op_par 3
r2 += r3 << 1;
// 199: (8) INSTR_XOR_C r4 imm 505016901
r4 ^= 505016901;
// 200: (2) INSTR_MUL_R r2-> r3 op_par 2
r3 *= r2;
// 201: (5) INSTR_ADD_RS r2-> r6 op_par 2
r6 += r2 << 3;
// 202: (4) INSTR_XOR_R r5-> r4 op_par 5
r4 ^= r5;
// 203: (1) INSTR_SMULH_R r5-> r5 op_par 605803804
r5 = Exec.smulh(r5, r5);
result = (int) r5;
// 204: (8) INSTR_XOR_C r0 imm 806363430
r0 ^= 806363430;
// 205: (2) INSTR_MUL_R r4-> r2 op_par 4
r2 *= r4;
// 206: (8) INSTR_XOR_C r4 imm 956093245
r4 ^= 956093245;
// 207: (6) INSTR_ROR_C r6 imm 28
r6 = Long.rotateRight(r6, 28);
// 208: (2) INSTR_MUL_R r7-> r0 op_par 7
r0 *= r7;
// 209: (10) INSTR_BRANCH imm 5771264
if (x == 0 && branch_enable && ((result & 5771264) == 0)) {
// branch to target from 209
    branch_enable = false;
} else {
    break;
}
} // for loop
// 210: (2) INSTR_MUL_R r1-> r4 op_par 1
r4 *= r1;
// 211: (8) INSTR_XOR_C r6 imm 744570668
r6 ^= 744570668;
// 212: (7) INSTR_ADD_C r1 imm 222669159
r1 += 222669159;
// 213: (0) INSTR_UMULH_R r2-> r3 op_par 521301372
r3 = Exec.umulh(r3, r2);
result = (int) r3;
// 214: (8) INSTR_XOR_C r5 imm -933202589
r5 ^= -933202589;
// 215: (2) INSTR_MUL_R r5-> r1 op_par 5
r1 *= r5;
// 216: (7) INSTR_ADD_C r7 imm 156850044
r7 += 156850044;
// 217: (3) INSTR_SUB_R r0-> r2 op_par 0
r2 -= r0;
// 218: (2) INSTR_MUL_R r0-> r5 op_par 0
r5 *= r0;
// 219: (3) INSTR_SUB_R r2-> r0 op_par 2
r0 -= r2;
// 220: (7) INSTR_ADD_C r4 imm 1134901773
r4 += 1134901773;
// 221: (2) INSTR_MUL_R r4-> r6 op_par 4
r6 *= r4;
// 222: (7) INSTR_ADD_C r2 imm 38984725
r2 += 38984725;
// 223: (8) INSTR_XOR_C r7 imm -902726083
r7 ^= -902726083;
// 224: (2) INSTR_MUL_R r7-> r3 op_par 7
r3 *= r7;
// 225: (9) INSTR_TARGET imm 0
// branch target 225
for (int x = 0; x < 2; x++) {
// 226: (2) INSTR_MUL_R r2-> r7 op_par 2
r7 *= r2;
// 227: (7) INSTR_ADD_C r1 imm -1271837069
r1 += -1271837069;
// 228: (8) INSTR_XOR_C r0 imm -650876034
r0 ^= -650876034;
// 229: (2) INSTR_MUL_R r2-> r0 op_par 2
r0 *= r2;
// 230: (6) INSTR_ROR_C r1 imm 59
r1 = Long.rotateRight(r1, 59);
// 231: (7) INSTR_ADD_C r6 imm -512622068
r6 += -512622068;
// 232: (2) INSTR_MUL_R r2-> r6 op_par 2
r6 *= r2;
// 233: (6) INSTR_ROR_C r3 imm 61
r3 = Long.rotateRight(r3, 61);
// 234: (7) INSTR_ADD_C r1 imm -821522727
r1 += -821522727;
// 235: (1) INSTR_SMULH_R r7-> r3 op_par -1765474686
r3 = Exec.smulh(r3, r7);
result = (int) r3;
// 236: (8) INSTR_XOR_C r1 imm -1432766069
r1 ^= -1432766069;
// 237: (2) INSTR_MUL_R r7-> r4 op_par 7
r4 *= r7;
// 238: (4) INSTR_XOR_R r2-> r7 op_par 2
r7 ^= r2;
// 239: (3) INSTR_SUB_R r2-> r1 op_par 2
r1 -= r2;
// 240: (2) INSTR_MUL_R r6-> r2 op_par 6
r2 *= r6;
// 241: (10) INSTR_BRANCH imm 135266372
if (x == 0 && branch_enable && ((result & 135266372) == 0)) {
// branch to target from 241
    branch_enable = false;
} else {
    break;
}
} // for loop
// 242: (2) INSTR_MUL_R r5-> r7 op_par 5
r7 *= r5;
// 243: (3) INSTR_SUB_R r0-> r1 op_par 0
r1 -= r0;
// 244: (8) INSTR_XOR_C r0 imm 1470497887
r0 ^= 1470497887;
// 245: (0) INSTR_UMULH_R r6-> r1 op_par -1094615237
r1 = Exec.umulh(r1, r6);
result = (int) r1;
// 246: (4) INSTR_XOR_R r5-> r6 op_par 5
r6 ^= r5;
// 247: (2) INSTR_MUL_R r2-> r3 op_par 2
r3 *= r2;
// 248: (8) INSTR_XOR_C r5 imm 677086695
r5 ^= 677086695;
// 249: (4) INSTR_XOR_R r0-> r2 op_par 0
r2 ^= r0;
// 250: (2) INSTR_MUL_R r5-> r6 op_par 5
r6 *= r5;
// 251: (3) INSTR_SUB_R r2-> r4 op_par 2
r4 -= r2;
// 252: (4) INSTR_XOR_R r5-> r0 op_par 5
r0 ^= r5;
// 253: (2) INSTR_MUL_R r2-> r0 op_par 2
r0 *= r2;
// 254: (8) INSTR_XOR_C r2 imm 1782120219
r2 ^= 1782120219;
// 255: (7) INSTR_ADD_C r4 imm 359273224
r4 += 359273224;
// 256: (2) INSTR_MUL_R r3-> r2 op_par 3
r2 *= r3;
// 257: (9) INSTR_TARGET imm 0
// branch target 257
for (int x = 0; x < 2; x++) {
// 258: (2) INSTR_MUL_R r5-> r1 op_par 5
r1 *= r5;
// 259: (6) INSTR_ROR_C r7 imm 40
r7 = Long.rotateRight(r7, 40);
// 260: (8) INSTR_XOR_C r4 imm 2077435484
r4 ^= 2077435484;
// 261: (2) INSTR_MUL_R r5-> r4 op_par 5
r4 *= r5;
// 262: (3) INSTR_SUB_R r6-> r5 op_par 6
r5 -= r6;
// 263: (4) INSTR_XOR_R r7-> r6 op_par 7
r6 ^= r7;
// 264: (2) INSTR_MUL_R r6-> r7 op_par 6
r7 *= r6;
// 265: (4) INSTR_XOR_R r6-> r5 op_par 6
r5 ^= r6;
// 266: (8) INSTR_XOR_C r6 imm 727391694
r6 ^= 727391694;
// 267: (0) INSTR_UMULH_R r2-> r1 op_par -334293119
r1 = Exec.umulh(r1, r2);
result = (int) r1;
// 268: (8) INSTR_XOR_C r5 imm -808044903
r5 ^= -808044903;
// 269: (2) INSTR_MUL_R r2-> r5 op_par 2
r5 *= r2;
// 270: (3) INSTR_SUB_R r3-> r4 op_par 3
r4 -= r3;
// 271: (6) INSTR_ROR_C r0 imm 63
r0 = Long.rotateRight(r0, 63);
// 272: (2) INSTR_MUL_R r6-> r4 op_par 6
r4 *= r6;
// 273: (10) INSTR_BRANCH imm 637534224
if (x == 0 && branch_enable && ((result & 637534224) == 0)) {
// branch to target from 273
    branch_enable = false;
} else {
    break;
}
} // for loop
// 274: (2) INSTR_MUL_R r2-> r6 op_par 2
r6 *= r2;
// 275: (7) INSTR_ADD_C r3 imm -1535332292
r3 += -1535332292;
// 276: (3) INSTR_SUB_R r2-> r0 op_par 2
r0 -= r2;
// 277: (1) INSTR_SMULH_R r5-> r0 op_par 1936971688
r0 = Exec.smulh(r0, r5);
result = (int) r0;
// 278: (7) INSTR_ADD_C r5 imm 146749843
r5 += 146749843;
// 279: (2) INSTR_MUL_R r1-> r5 op_par 1
r5 *= r1;
// 280: (5) INSTR_ADD_RS r4-> r7 op_par 4
r7 += r4 << 3;
// 281: (7) INSTR_ADD_C r4 imm 2020853372
r4 += 2020853372;
// 282: (2) INSTR_MUL_R r2-> r1 op_par 2
r1 *= r2;
// 283: (8) INSTR_XOR_C r2 imm 734641461
r2 ^= 734641461;
// 284: (7) INSTR_ADD_C r6 imm 1565611569
r6 += 1565611569;
// 285: (2) INSTR_MUL_R r2-> r4 op_par 2
r4 *= r2;
// 286: (6) INSTR_ROR_C r6 imm 63
r6 = Long.rotateRight(r6, 63);
// 287: (7) INSTR_ADD_C r7 imm 838919191
r7 += 838919191;
// 288: (2) INSTR_MUL_R r0-> r2 op_par 0
r2 *= r0;
// 289: (9) INSTR_TARGET imm 0
// branch target 289
for (int x = 0; x < 2; x++) {
// 290: (2) INSTR_MUL_R r5-> r6 op_par 5
r6 *= r5;
// 291: (8) INSTR_XOR_C r0 imm -1002279369
r0 ^= -1002279369;
// 292: (3) INSTR_SUB_R r5-> r3 op_par 5
r3 -= r5;
// 293: (2) INSTR_MUL_R r4-> r7 op_par 4
r7 *= r4;
// 294: (6) INSTR_ROR_C r3 imm 55
r3 = Long.rotateRight(r3, 55);
// 295: (3) INSTR_SUB_R r5-> r0 op_par 5
r0 -= r5;
// 296: (2) INSTR_MUL_R r2-> r0 op_par 2
r0 *= r2;
// 297: (8) INSTR_XOR_C r1 imm 870112856
r1 ^= 870112856;
// 298: (3) INSTR_SUB_R r2-> r5 op_par 2
r5 -= r2;
// 299: (1) INSTR_SMULH_R r6-> r2 op_par 328755916
r2 = Exec.smulh(r2, r6);
result = (int) r2;
// 300: (4) INSTR_XOR_R r5-> r6 op_par 5
r6 ^= r5;
// 301: (2) INSTR_MUL_R r4-> r6 op_par 4
r6 *= r4;
// 302: (8) INSTR_XOR_C r3 imm 2108382988
r3 ^= 2108382988;
// 303: (6) INSTR_ROR_C r1 imm 7
r1 = Long.rotateRight(r1, 7);
// 304: (2) INSTR_MUL_R r5-> r3 op_par 5
r3 *= r5;
// 305: (10) INSTR_BRANCH imm 335546370
if (x == 0 && branch_enable && ((result & 335546370) == 0)) {
// branch to target from 305
    branch_enable = false;
} else {
    break;
}
} // for loop
// 306: (2) INSTR_MUL_R r4-> r1 op_par 4
r1 *= r4;
// 307: (5) INSTR_ADD_RS r5-> r4 op_par 5
r4 += r5 << 0;
// 308: (4) INSTR_XOR_R r7-> r5 op_par 7
r5 ^= r7;
// 309: (1) INSTR_SMULH_R r7-> r4 op_par -222426586
r4 = Exec.smulh(r4, r7);
result = (int) r4;
// 310: (6) INSTR_ROR_C r0 imm 60
r0 = Long.rotateRight(r0, 60);
// 311: (2) INSTR_MUL_R r2-> r5 op_par 2
r5 *= r2;
// 312: (8) INSTR_XOR_C r6 imm 285209259
r6 ^= 285209259;
// 313: (3) INSTR_SUB_R r7-> r2 op_par 7
r2 -= r7;
// 314: (2) INSTR_MUL_R r1-> r2 op_par 1
r2 *= r1;
// 315: (8) INSTR_XOR_C r3 imm 1817846395
r3 ^= 1817846395;
// 316: (7) INSTR_ADD_C r0 imm -2103332825
r0 += -2103332825;
// 317: (2) INSTR_MUL_R r6-> r0 op_par 6
r0 *= r6;
// 318: (7) INSTR_ADD_C r3 imm 1213453856
r3 += 1213453856;
// 319: (4) INSTR_XOR_R r7-> r1 op_par 7
r1 ^= r7;
// 320: (2) INSTR_MUL_R r7-> r3 op_par 7
r3 *= r7;
// 321: (9) INSTR_TARGET imm 0
// branch target 321
for (int x = 0; x < 2; x++) {
// 322: (2) INSTR_MUL_R r5-> r4 op_par 5
r4 *= r5;
// 323: (6) INSTR_ROR_C r6 imm 4
r6 = Long.rotateRight(r6, 4);
// 324: (7) INSTR_ADD_C r5 imm 990203827
r5 += 990203827;
// 325: (2) INSTR_MUL_R r6-> r5 op_par 6
r5 *= r6;
// 326: (8) INSTR_XOR_C r6 imm -1696949885
r6 ^= -1696949885;
// 327: (4) INSTR_XOR_R r0-> r2 op_par 0
r2 ^= r0;
// 328: (2) INSTR_MUL_R r2-> r1 op_par 2
r1 *= r2;
// 329: (5) INSTR_ADD_RS r0-> r7 op_par 0
r7 += r0 << 3;
// 330: (6) INSTR_ROR_C r3 imm 13
r3 = Long.rotateRight(r3, 13);
// 331: (1) INSTR_SMULH_R r0-> r0 op_par 167054591
r0 = Exec.smulh(r0, r0);
result = (int) r0;
// 332: (8) INSTR_XOR_C r7 imm -493235271
r7 ^= -493235271;
// 333: (2) INSTR_MUL_R r7-> r2 op_par 7
r2 *= r7;
// 334: (8) INSTR_XOR_C r5 imm -1574512584
r5 ^= -1574512584;
// 335: (7) INSTR_ADD_C r7 imm 27680815
r7 += 27680815;
// 336: (2) INSTR_MUL_R r4-> r7 op_par 4
r7 *= r4;
// 337: (10) INSTR_BRANCH imm 671088832
if (x == 0 && branch_enable && ((result & 671088832) == 0)) {
// branch to target from 337
    branch_enable = false;
} else {
    break;
}
} // for loop
// 338: (2) INSTR_MUL_R r1-> r3 op_par 1
r3 *= r1;
// 339: (6) INSTR_ROR_C r6 imm 14
r6 = Long.rotateRight(r6, 14);
// 340: (5) INSTR_ADD_RS r1-> r4 op_par 1
r4 += r1 << 3;
// 341: (1) INSTR_SMULH_R r0-> r0 op_par 706003417
r0 = Exec.smulh(r0, r0);
result = (int) r0;
// 342: (7) INSTR_ADD_C r1 imm 686820064
r1 += 686820064;
// 343: (2) INSTR_MUL_R r4-> r1 op_par 4
r1 *= r4;
// 344: (6) INSTR_ROR_C r7 imm 19
r7 = Long.rotateRight(r7, 19);
// 345: (7) INSTR_ADD_C r2 imm 256810477
r2 += 256810477;
// 346: (2) INSTR_MUL_R r4-> r5 op_par 4
r5 *= r4;
// 347: (5) INSTR_ADD_RS r3-> r4 op_par 3
r4 += r3 << 1;
// 348: (6) INSTR_ROR_C r3 imm 35
r3 = Long.rotateRight(r3, 35);
// 349: (2) INSTR_MUL_R r7-> r2 op_par 7
r2 *= r7;
// 350: (8) INSTR_XOR_C r6 imm 1212047864
r6 ^= 1212047864;
// 351: (4) INSTR_XOR_R r4-> r3 op_par 4
r3 ^= r4;
// 352: (2) INSTR_MUL_R r1-> r3 op_par 1
r3 *= r1;
// 353: (9) INSTR_TARGET imm 0
// branch target 353
for (int x = 0; x < 2; x++) {
// 354: (2) INSTR_MUL_R r0-> r7 op_par 0
r7 *= r0;
// 355: (6) INSTR_ROR_C r4 imm 8
r4 = Long.rotateRight(r4, 8);
// 356: (7) INSTR_ADD_C r1 imm -55606909
r1 += -55606909;
// 357: (2) INSTR_MUL_R r4-> r1 op_par 4
r1 *= r4;
// 358: (8) INSTR_XOR_C r5 imm -1984155640
r5 ^= -1984155640;
// 359: (4) INSTR_XOR_R r6-> r2 op_par 6
r2 ^= r6;
// 360: (2) INSTR_MUL_R r0-> r2 op_par 0
r2 *= r0;
// 361: (3) INSTR_SUB_R r4-> r0 op_par 4
r0 -= r4;
// 362: (6) INSTR_ROR_C r6 imm 59
r6 = Long.rotateRight(r6, 59);
// 363: (0) INSTR_UMULH_R r0-> r6 op_par 1065414024
r6 = Exec.umulh(r6, r0);
result = (int) r6;
// 364: (7) INSTR_ADD_C r3 imm 956369973
r3 += 956369973;
// 365: (2) INSTR_MUL_R r3-> r5 op_par 3
r5 *= r3;
// 366: (8) INSTR_XOR_C r3 imm 1970891931
r3 ^= 1970891931;
// 367: (4) INSTR_XOR_R r1-> r4 op_par 1
r4 ^= r1;
// 368: (2) INSTR_MUL_R r4-> r0 op_par 4
r0 *= r4;
// 369: (10) INSTR_BRANCH imm 1073881090
if (x == 0 && branch_enable && ((result & 1073881090) == 0)) {
// branch to target from 369
    branch_enable = false;
} else {
    break;
}
} // for loop
// 370: (2) INSTR_MUL_R r4-> r3 op_par 4
r3 *= r4;
// 371: (8) INSTR_XOR_C r4 imm -1033719878
r4 ^= -1033719878;
// 372: (7) INSTR_ADD_C r1 imm 745965937
r1 += 745965937;
// 373: (0) INSTR_UMULH_R r1-> r1 op_par -2092951798
r1 = Exec.umulh(r1, r1);
result = (int) r1;
// 374: (7) INSTR_ADD_C r5 imm -116221057
r5 += -116221057;
// 375: (2) INSTR_MUL_R r6-> r5 op_par 6
r5 *= r6;
// 376: (8) INSTR_XOR_C r6 imm -923354425
r6 ^= -923354425;
// 377: (7) INSTR_ADD_C r2 imm -2113538797
r2 += -2113538797;
// 378: (2) INSTR_MUL_R r7-> r4 op_par 7
r4 *= r7;
// 379: (7) INSTR_ADD_C r7 imm -1776301717
r7 += -1776301717;
// 380: (8) INSTR_XOR_C r3 imm 430017970
r3 ^= 430017970;
// 381: (2) INSTR_MUL_R r7-> r6 op_par 7
r6 *= r7;
// 382: (5) INSTR_ADD_RS r7-> r3 op_par 7
r3 += r7 << 0;
// 383: (7) INSTR_ADD_C r0 imm 309496090
r0 += 309496090;
// 384: (2) INSTR_MUL_R r0-> r1 op_par 0
r1 *= r0;
// 385: (9) INSTR_TARGET imm 0
// branch target 385
for (int x = 0; x < 2; x++) {
// 386: (2) INSTR_MUL_R r4-> r7 op_par 4
r7 *= r4;
// 387: (3) INSTR_SUB_R r3-> r2 op_par 3
r2 -= r3;
// 388: (4) INSTR_XOR_R r4-> r0 op_par 4
r0 ^= r4;
// 389: (2) INSTR_MUL_R r6-> r3 op_par 6
r3 *= r6;
// 390: (3) INSTR_SUB_R r4-> r5 op_par 4
r5 -= r4;
// 391: (6) INSTR_ROR_C r4 imm 49
r4 = Long.rotateRight(r4, 49);
// 392: (2) INSTR_MUL_R r0-> r4 op_par 0
r4 *= r0;
// 393: (8) INSTR_XOR_C r0 imm -470988448
r0 ^= -470988448;
// 394: (7) INSTR_ADD_C r5 imm -1554723188
r5 += -1554723188;
// 395: (1) INSTR_SMULH_R r2-> r7 op_par -637566232
r7 = Exec.smulh(r7, r2);
result = (int) r7;
// 396: (5) INSTR_ADD_RS r1-> r6 op_par 1
r6 += r1 << 3;
// 397: (2) INSTR_MUL_R r3-> r2 op_par 3
r2 *= r3;
// 398: (3) INSTR_SUB_R r3-> r1 op_par 3
r1 -= r3;
// 399: (7) INSTR_ADD_C r0 imm 993176950
r0 += 993176950;
// 400: (2) INSTR_MUL_R r1-> r6 op_par 1
r6 *= r1;
// 401: (10) INSTR_BRANCH imm 268436489
if (x == 0 && branch_enable && ((result & 268436489) == 0)) {
// branch to target from 401
    branch_enable = false;
} else {
    break;
}
} // for loop
// 402: (2) INSTR_MUL_R r3-> r0 op_par 3
r0 *= r3;
// 403: (8) INSTR_XOR_C r4 imm -29769747
r4 ^= -29769747;
// 404: (6) INSTR_ROR_C r1 imm 62
r1 = Long.rotateRight(r1, 62);
// 405: (0) INSTR_UMULH_R r3-> r3 op_par 301806834
r3 = Exec.umulh(r3, r3);
result = (int) r3;
// 406: (8) INSTR_XOR_C r5 imm 183367336
r5 ^= 183367336;
// 407: (2) INSTR_MUL_R r1-> r7 op_par 1
r7 *= r1;
// 408: (7) INSTR_ADD_C r1 imm 829517279
r1 += 829517279;
// 409: (8) INSTR_XOR_C r2 imm 1997495090
r2 ^= 1997495090;
// 410: (2) INSTR_MUL_R r4-> r2 op_par 4
r2 *= r4;
// 411: (8) INSTR_XOR_C r0 imm 1433636533
r0 ^= 1433636533;
// 412: (5) INSTR_ADD_RS r5-> r1 op_par 5
r1 += r5 << 1;
// 413: (2) INSTR_MUL_R r4-> r0 op_par 4
r0 *= r4;
// 414: (6) INSTR_ROR_C r6 imm 58
r6 = Long.rotateRight(r6, 58);
// 415: (7) INSTR_ADD_C r5 imm 770122383
r5 += 770122383;
// 416: (2) INSTR_MUL_R r6-> r3 op_par 6
r3 *= r6;
// 417: (9) INSTR_TARGET imm 0
// branch target 417
for (int x = 0; x < 2; x++) {
// 418: (2) INSTR_MUL_R r4-> r1 op_par 4
r1 *= r4;
// 419: (4) INSTR_XOR_R r5-> r2 op_par 5
r2 ^= r5;
// 420: (7) INSTR_ADD_C r6 imm 1716746173
r6 += 1716746173;
// 421: (2) INSTR_MUL_R r7-> r6 op_par 7
r6 *= r7;
// 422: (8) INSTR_XOR_C r2 imm -1899205619
r2 ^= -1899205619;
// 423: (6) INSTR_ROR_C r0 imm 4
r0 = Long.rotateRight(r0, 4);
// 424: (2) INSTR_MUL_R r3-> r2 op_par 3
r2 *= r3;
// 425: (5) INSTR_ADD_RS r3-> r4 op_par 3
r4 += r3 << 0;
// 426: (8) INSTR_XOR_C r5 imm -2081298754
r5 ^= -2081298754;
// 427: (1) INSTR_SMULH_R r1-> r7 op_par -1445980940
r7 = Exec.smulh(r7, r1);
result = (int) r7;
// 428: (7) INSTR_ADD_C r0 imm 1506566984
r0 += 1506566984;
// 429: (2) INSTR_MUL_R r6-> r0 op_par 6
r0 *= r6;
// 430: (5) INSTR_ADD_RS r3-> r1 op_par 3
r1 += r3 << 2;
// 431: (8) INSTR_XOR_C r4 imm -2050319838
r4 ^= -2050319838;
// 432: (2) INSTR_MUL_R r5-> r1 op_par 5
r1 *= r5;
// 433: (10) INSTR_BRANCH imm 33921
if (x == 0 && branch_enable && ((result & 33921) == 0)) {
// branch to target from 433
    branch_enable = false;
} else {
    break;
}
} // for loop
// 434: (2) INSTR_MUL_R r4-> r5 op_par 4
r5 *= r4;
// 435: (8) INSTR_XOR_C r6 imm -1694535246
r6 ^= -1694535246;
// 436: (7) INSTR_ADD_C r4 imm 242558395
r4 += 242558395;
// 437: (0) INSTR_UMULH_R r6-> r6 op_par -1405725806
r6 = Exec.umulh(r6, r6);
result = (int) r6;
// 438: (7) INSTR_ADD_C r7 imm -1675755906
r7 += -1675755906;
// 439: (2) INSTR_MUL_R r7-> r4 op_par 7
r4 *= r7;
// 440: (6) INSTR_ROR_C r7 imm 53
r7 = Long.rotateRight(r7, 53);
// 441: (8) INSTR_XOR_C r3 imm -586895056
r3 ^= -586895056;
// 442: (2) INSTR_MUL_R r2-> r7 op_par 2
r7 *= r2;
// 443: (7) INSTR_ADD_C r5 imm -1668159636
r5 += -1668159636;
// 444: (6) INSTR_ROR_C r1 imm 46
r1 = Long.rotateRight(r1, 46);
// 445: (2) INSTR_MUL_R r0-> r3 op_par 0
r3 *= r0;
// 446: (7) INSTR_ADD_C r2 imm 1618535678
r2 += 1618535678;
// 447: (5) INSTR_ADD_RS r1-> r0 op_par 1
r0 += r1 << 0;
// 448: (2) INSTR_MUL_R r4-> r0 op_par 4
r0 *= r4;
// 449: (9) INSTR_TARGET imm 0
// branch target 449
for (int x = 0; x < 2; x++) {
// 450: (2) INSTR_MUL_R r5-> r1 op_par 5
r1 *= r5;
// 451: (8) INSTR_XOR_C r5 imm -2119728329
r5 ^= -2119728329;
// 452: (3) INSTR_SUB_R r7-> r2 op_par 7
r2 -= r7;
// 453: (2) INSTR_MUL_R r6-> r2 op_par 6
r2 *= r6;
// 454: (3) INSTR_SUB_R r3-> r5 op_par 3
r5 -= r3;
// 455: (7) INSTR_ADD_C r7 imm 868850432
r7 += 868850432;
// 456: (2) INSTR_MUL_R r5-> r6 op_par 5
r6 *= r5;
// 457: (8) INSTR_XOR_C r4 imm 1033184863
r4 ^= 1033184863;
// 458: (7) INSTR_ADD_C r3 imm -1706359847
r3 += -1706359847;
// 459: (1) INSTR_SMULH_R r0-> r5 op_par 1640007259
r5 = Exec.smulh(r5, r0);
result = (int) r5;
// 460: (8) INSTR_XOR_C r3 imm -120933607
r3 ^= -120933607;
// 461: (2) INSTR_MUL_R r1-> r3 op_par 1
r3 *= r1;
// 462: (8) INSTR_XOR_C r0 imm 1178258131
r0 ^= 1178258131;
// 463: (3) INSTR_SUB_R r4-> r1 op_par 4
r1 -= r4;
// 464: (2) INSTR_MUL_R r0-> r1 op_par 0
r1 *= r0;
// 465: (10) INSTR_BRANCH imm 369098768
if (x == 0 && branch_enable && ((result & 369098768) == 0)) {
// branch to target from 465
    branch_enable = false;
} else {
    break;
}
} // for loop
// 466: (2) INSTR_MUL_R r2-> r7 op_par 2
r7 *= r2;
// 467: (3) INSTR_SUB_R r6-> r2 op_par 6
r2 -= r6;
// 468: (8) INSTR_XOR_C r6 imm 218026015
r6 ^= 218026015;
// 469: (0) INSTR_UMULH_R r3-> r6 op_par 1659197441
r6 = Exec.umulh(r6, r3);
result = (int) r6;
// 470: (7) INSTR_ADD_C r0 imm 564480461
r0 += 564480461;
// 471: (2) INSTR_MUL_R r3-> r2 op_par 3
r2 *= r3;
// 472: (7) INSTR_ADD_C r1 imm 1775430708
r1 += 1775430708;
// 473: (8) INSTR_XOR_C r0 imm 2009756166
r0 ^= 2009756166;
// 474: (2) INSTR_MUL_R r5-> r0 op_par 5
r0 *= r5;
// 475: (7) INSTR_ADD_C r4 imm 1879834739
r4 += 1879834739;
// 476: (6) INSTR_ROR_C r1 imm 54
r1 = Long.rotateRight(r1, 54);
// 477: (2) INSTR_MUL_R r3-> r1 op_par 3
r1 *= r3;
// 478: (7) INSTR_ADD_C r3 imm 1716190984
r3 += 1716190984;
// 479: (3) INSTR_SUB_R r5-> r4 op_par 5
r4 -= r5;
// 480: (2) INSTR_MUL_R r3-> r4 op_par 3
r4 *= r3;
// 481: (9) INSTR_TARGET imm 0
// branch target 481
for (int x = 0; x < 2; x++) {
// 482: (2) INSTR_MUL_R r0-> r6 op_par 0
r6 *= r0;
// 483: (8) INSTR_XOR_C r2 imm -180218157
r2 ^= -180218157;
// 484: (7) INSTR_ADD_C r5 imm -1563641237
r5 += -1563641237;
// 485: (2) INSTR_MUL_R r2-> r5 op_par 2
r5 *= r2;
// 486: (7) INSTR_ADD_C r1 imm -1453310155
r1 += -1453310155;
// 487: (3) INSTR_SUB_R r2-> r0 op_par 2
r0 -= r2;
// 488: (2) INSTR_MUL_R r0-> r1 op_par 0
r1 *= r0;
// 489: (3) INSTR_SUB_R r2-> r3 op_par 2
r3 -= r2;
// 490: (6) INSTR_ROR_C r2 imm 37
r2 = Long.rotateRight(r2, 37);
// 491: (0) INSTR_UMULH_R r2-> r4 op_par -2115487513
r4 = Exec.umulh(r4, r2);
result = (int) r4;
// 492: (3) INSTR_SUB_R r2-> r6 op_par 2
r6 -= r2;
// 493: (2) INSTR_MUL_R r0-> r3 op_par 0
r3 *= r0;
// 494: (7) INSTR_ADD_C r6 imm 224406753
r6 += 224406753;
// 495: (5) INSTR_ADD_RS r5-> r0 op_par 5
r0 += r5 << 0;
// 496: (2) INSTR_MUL_R r2-> r0 op_par 2
r0 *= r2;
// 497: (10) INSTR_BRANCH imm -2079842304
if (x == 0 && branch_enable && ((result & -2079842304) == 0)) {
// branch to target from 497
    branch_enable = false;
} else {
    break;
}
} // for loop
// 498: (2) INSTR_MUL_R r7-> r6 op_par 7
r6 *= r7;
// 499: (7) INSTR_ADD_C r1 imm -1191538956
r1 += -1191538956;
// 500: (5) INSTR_ADD_RS r5-> r2 op_par 5
r2 += r5 << 0;
// 501: (1) INSTR_SMULH_R r3-> r4 op_par 1637499086
r4 = Exec.smulh(r4, r3);
result = (int) r4;
// 502: (8) INSTR_XOR_C r7 imm -290778119
r7 ^= -290778119;
// 503: (2) INSTR_MUL_R r2-> r7 op_par 2
r7 *= r2;
// 504: (6) INSTR_ROR_C r2 imm 15
r2 = Long.rotateRight(r2, 15);
// 505: (8) INSTR_XOR_C r3 imm 1526367699
r3 ^= 1526367699;
// 506: (2) INSTR_MUL_R r1-> r3 op_par 1
r3 *= r1;
// 507: (7) INSTR_ADD_C r6 imm -635450595
r6 += -635450595;
// 508: (8) INSTR_XOR_C r0 imm -744570129
r0 ^= -744570129;
// 509: (2) INSTR_MUL_R r5-> r0 op_par 5
r0 *= r5;
// 510: (3) INSTR_SUB_R r1-> r2 op_par 1
r2 -= r1;
// 511: (8) INSTR_XOR_C r1 imm 554157631
r1 ^= 554157631;
r[0] = r0;
r[1] = r1;
r[2] = r2;
r[3] = r3;
r[4] = r4;
r[5] = r5;
r[6] = r6;
r[7] = r7;
}
}
