# Fraction
A simple fraction library for Java and Android.

## Introduction

Fraction is an immutable class for storing rational number values in their discrete states, with automatic reduction of fractions (e.g., 2/4 becomes 1/2), and basic mathematical operations between other Fraction objects and primitive number values.

The library internally uses `BigInteger` to store values to ensure precision and prevent overflow when dealing with large numerators and denominators.

### Prerequisites

Android development environment with the jcenter repository added in the project's build.gradle file.
A target SDK of 1 or higher.

```
repositories {
        jcenter()
    }
```

### Installation

Add the following dependency in the app's build.gradle file:

```
dependencies {
    compile 'net.scarlettsystems.android:fraction:0.0.2'
}
```

## Usage
Instantiate a `Fraction` object by specifying the numerator and denominator as `BigInteger` objects, or `long` values:

```Java
Fraction half = new Fraction(1, 2);
Fraction third = new Fraction(BigInteger.valueOf(1), BigInteger.valueOf(3));
```
`Fraction` supports basic mathematical operators that preserve the discrete precision of the fraction:

```Java
Fraction fiveSixths = half.add(third);
Fraction oneSixth = half.subtract(third);
Fraction quarter = half.pow(2);
```

Use the `getValue()` method to resolve the fraction into a double value with a specified precision:

```Java
double pointFive = half.getValue(1);
```

## Versioning

Current version: [ ![Download](https://api.bintray.com/packages/shanescarlett/Fraction/fraction/images/download.svg) ](https://bintray.com/shanescarlett/Fraction/fraction/_latestVersion)

## Authors

* **Shane Scarlett** - *core development* - [Scarlett Systems](https://scarlettsystems.net)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the Apache 2.0 License
