# Innowise-Applied-Music-Theory-Task

Applied Music Theory Task

## Task Requirements

All the explanations, requirements and examples are provided in the attached documents **IntervalsTaskRequirements** and **examples**, but in short I had to implement the following functions:

**intervalConstruction**
- The function 'intervalConstruction' must take an array of strings as input and return a string.
- An array contains three or two elements.
- The first element in an array is an interval name, the second is a starting note, and the third indicates whether an interval is ascending or descending.
- If there is no third element in an array, the building order is ascending by default.
- The function should return a string containing a note name.
- Only the following note names are allowed in a return string: <br>
Cbb Cb C C# C## Dbb Db D D# D## Ebb Eb E E# E## Fbb Fb F F# F## Gbb Gb G G# G## Abb Ab A A# A## Bbb Bb B B# B## 
- If there are more or fewer elements in the input array, an exception should be thrown: "Illegal number of elements in input array"

**intervalIdentification** 
- The function 'intervalIdentification' must take an array of strings as input and return a string.
- An array contains three or two elements.
- The first element is the first note in the interval, the second element is the last note in the interval, the third indicates whether an interval is ascending or descending.
- If there is no third element in an array, the interval is considered ascending by default.
- The function should return a string - name of the interval.
- Only the following intervals are allowed in a return string: <br>
m2 M2 m3 M3 P4 P5 m6 M6 m7 M7 P8
- If the interval does not fit a description, an exception should be thrown: "Cannot identify the interval".

Class Name: `Intervals` <br>
Functions `intervalIdentification` and `intervalConstruction` `MUST` be static <br>
Do not add packages <br>
Java Version: 14 and older <br>
Do not use `System.out.*` or any other output methods<br>
Do not add packages <br>
Use predefined file <br>

## Installation

You are proposed to use Java 11.

![This is an image](https://i.ibb.co/f1HC8RZ/image.png)

Current Maven version.

![This is an image](https://i.ibb.co/5M5bxcm/image.png)

There're the steps to unpack my project: 

* git clone Innowise-Applied-Music-Theory-Task

* Open it via your IDE

## Usage

Start maven tests:

* Open the folder in which you cloned the project

* mvn clean test 