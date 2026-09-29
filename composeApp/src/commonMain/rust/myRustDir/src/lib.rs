mod test;

mod code;

use rand::{RngExt, SeedableRng};
use std::time::SystemTime;
use crate::code::getCode;

uniffi::setup_scaffolding!(); //Generates extra stuff needed for uniffi

//expose with uniffi via lib as it wont be executed directly

#[uniffi::export]
fn peekCode() -> u64{
    getCode(vec![])
}

#[uniffi::export] //no commonMain exit so this is placeholder to force KMP closure
fn abortExit(){
    std::process::exit(0);
}



