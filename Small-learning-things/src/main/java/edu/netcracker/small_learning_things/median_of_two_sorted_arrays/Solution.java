package edu.netcracker.small_learning_things.median_of_two_sorted_arrays;

/**
 * @author svku0919
 * @version 10.01.2023-14:24
 */

class Solution {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            if (nums2.length % 2 == 0) {
                return (nums2[nums2.length / 2] + nums2[nums2.length / 2 - 1]) / 2.0;
            } else {
                return nums2[nums2.length / 2];
            }
        } else if (nums1.length == 1 && nums2.length > 1) {
            int middleLeft2 = nums2[nums2.length / 2 - 1];
            int middleRight2 = nums2[nums2.length / 2];
            if (nums2.length % 2 == 0) {
                if (middleRight2 >= nums1[0] && middleLeft2 <= nums1[0]) {
                    return nums1[0];
                } else {
                    return middleRight2;
                }
            } else {
                if ((middleRight2 >= nums1[0] && middleLeft2 <= nums1[0]) || (middleRight2 <= nums1[0] && nums2[nums2.length / 2 + 1] >= nums1[0])) {
                    return (nums1[0] + middleRight2) / 2.0;
                } else if (middleRight2 >= nums1[0]) {
                    return (nums2[nums2.length / 2 + 1] + middleRight2) / 2.0;
                } else {
                    return (middleLeft2 + middleRight2) / 2.0;
                }
            }
        } else if (nums2.length == 1 && nums1.length == 1) {
            return (nums1[0] + nums2[0]) / 2.0;
        }

        if (nums2.length == 0) {
            if (nums1.length % 2 == 0) {
                return (nums1[nums1.length / 2 - 1] + nums1[nums1.length / 2]) / 2.0;
            } else {
                return nums1[nums1.length / 2];
            }
        } else if (nums2.length == 1) {
            int middleLeft1 = nums1[nums1.length / 2 - 1];
            int middleRight1 = nums1[nums1.length / 2];
            if (nums1.length % 2 == 0) {
                if (middleRight1 >= nums2[0] && middleLeft1 <= nums2[0]) {
                    return nums2[0];
                } else {
                    return middleRight1;
                }
            } else {
                if ((middleRight1 >= nums2[0] && middleLeft1 <= nums2[0]) || (middleRight1 <= nums2[0] && nums1[nums1.length / 2 + 1] >= nums2[0])) {
                    return (nums2[0] + middleRight1) / 2.0;
                } else if (middleRight1 >= nums2[0]) {
                    return (nums1[nums1.length / 2 + 1] + middleRight1) / 2.0;
                } else {
                    return (middleLeft1 + middleRight1) / 2.0;
                }
            }
        }

        int startI = 0;
        int endI = nums1.length - 1;
        int i = (endI - startI) / 2;

        int startJ = 0;
        int endJ = nums2.length - 1;
        int j = (endJ - startJ) / 2;

        boolean twoMedians = (nums1.length + nums2.length) % 2 == 0;
        boolean notFound = true;
        double median = 0;
        while (notFound) {
            int elemI = nums1[i];
            int elemJ = nums2[j];
            if (elemI > elemJ) {
                endI = i;
                startJ = j;
                i = startI + findMiddleIndex(startI, endI, true);
                j = startJ + findMiddleIndex(startJ, endJ, false);
            } else if (elemI < elemJ) {
                startI = i;
                endJ = j;
                i = startI + findMiddleIndex(startI, endI, false);
                j = startJ + findMiddleIndex(startJ, endJ, true);
            } else {
                if (nums1.length % 2 == 1 && nums2.length % 2 == 1) {
                    notFound = false;
                    median = elemI;
                } else {
                    notFound = false;
                    median = (elemI + Math.min(nums1[i + 1], nums2[j + 1])) / 2.0;
                }
            }

            if (endI - startI < 1 && !twoMedians) {
                notFound = false;
                median = nums1[startI];
            } else if (endJ - startJ < 1 && !twoMedians) {
                notFound = false;
                median = nums2[startJ];
            } else if ((i == startI || i == endI) && (j == startJ || j == endJ)) {
                notFound = false;
                median = (nums1[i] + nums2[j]) / 2.0;
            }

        }

        return median;
    }

    public static int findMiddleIndex(int startIndex, int endIndex, boolean nearestReturnZero) {
        if (endIndex - startIndex > 1) {
            return (endIndex - startIndex) % 2 == 0 ? (endIndex - startIndex) / 2 : (endIndex - startIndex + 1) / 2;
        } else if (endIndex == startIndex) {
            return 0;
        } else {
            return nearestReturnZero ? 0 : 1;
        }
    }

    public static void main(String[] args) {
        double medianSortedArrays = findMedianSortedArrays(new int[]{1,3}, new int[]{2,7});
        System.out.println(medianSortedArrays);
    }
}
