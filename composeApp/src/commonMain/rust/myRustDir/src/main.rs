

mod code;

//Copy the release elsewhere to gain a copy of existing code as a mock 2FA code
fn main(){
    println!("Code : {}",               //first arg is always name
        code::getCode( std::env::args().skip(1).collect::<Vec<_>>() )
    );
}