use std::fmt::UpperHex;
use crate::code::{getCode};
use rand::{RngExt, SeedableRng};
use std::thread::sleep;
use std::time::{Duration, SystemTime};
use crate::peekCode;

#[test]
fn testSeed(){
    let Gen = rand::rngs::ChaCha20Rng::seed_from_u64;

    assert_eq!(
        Gen(1),Gen(1)
    );
    assert_ne!(
        Gen(0),Gen(1)
    );

    //Ensure of a limited range with limited seed, will always same and even start same!
    assert!(
        Gen(5).random_range(5000..=5000).to_string().starts_with(
            Gen(5).random_range(5000..=5000).to_string().as_bytes()[0] as char
        )
    );

    for i in 0..600{
        if(i<300) {assert_eq!(i/300,0)}
        else if(i<600) {assert_eq!(i/300,1)}
        else{assert_eq!(i/300,2)};
    }
}
#[test]
fn testGen(){
    //offset 5s diff if would pass over for test
    if (SystemTime::now().duration_since(SystemTime::UNIX_EPOCH).unwrap().as_secs() % 300 >=295){
        sleep(Duration::from_secs(5))
    }

    assert_eq!(peekCode(), peekCode());

        //PeekCode must match the timestamp passed for it to work!
    assert_eq!(
        peekCode(),
        getCode(vec![chrono::Local::now().timestamp().to_string()])
    )
}

#[test] fn fmtGen(){
    assert_eq!(
        peekCode(),
        getCode(vec![
            format!("{}",chrono::Local::now().format("%Y-%m-%d-%H-%M")),
        ])
    )
}