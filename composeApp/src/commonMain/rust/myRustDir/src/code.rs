use rand::{RngExt, SeedableRng};
use std::time::SystemTime;
use chrono::TimeZone;

const min:u64 =1000;
const max:u64 =10000000000;

fn genPass(TSsecs:u64) -> u64{
    rand::rngs::ChaCha20Rng::seed_from_u64(
        TSsecs / 300u64
        //Shift so its within a divisible 5min group
    ).random_range(min..max)
}

pub(crate) fn getCode(args:Vec<String>) -> u64{
    match(args.len()){
        0 => {
            println!("Grabbing sys clock based code!");
            genPass(
                //Convert localtime to UTC same - regardless of location
                chrono::offset::Local::now().timestamp() as u64,
            )
        }
        1 => {
            //Try to format else run
            if let Some(t) =
                args.get(0)
                    .and_then(|x| x.parse::<i64>().ok() )
                    .and_then(|x| chrono::Local::timestamp_opt(& chrono::offset::Local,x,0).single() )
                        //handle YYYY-MM-DD-HH-MM to timestamp
                    .or_else(||
                        args[0].split("-").filter_map(|x|x.parse::<u32>().ok() ).collect::<Vec<_>>().try_into().ok() //gets type from where its sent to
                            .and_then(|[Y,M,D,h,m] : [u32;5]| chrono::offset::Local.with_ymd_and_hms(
                                Y as i32,
                                M,
                                D,
                                h,
                                m,
                            0
                        ).single())
                    )
            {
                println!("TimeStamp code!");
                genPass( t.timestamp() as u64 )
            }else{
                println!("Err parsing timestamp");
                rand::random_range(0..min)
            }
        }
        _ => {
            println!("Err! 1 timestamp argument max!");
            rand::random_range(0..min)
        }
    }
}