use book_ex;

create table tbl_member(
	userId varchar(50) not null,
	userPw varchar(50) not null,
	userName varchar(50) not null,
	email varchar(50),
	regdate timestamp default now(),
	updatedate timestamp default now(),
	primary key(userId)
);

select * from tbl_member;



create table tbl_board(
	bno int not null auto_increment,
	title varchar(200) not null,
	content text null,
	writer varchar(50) not null,
	regdate timestamp not null default now(),
	viewcnt int default 0,
	primary key (bno)
)

use book_ex;
select * from tbl_board;


select viewcnt from tbl_board where bno = 6;
update tbl_board set viewcnt = (viewcnt + 1) where bno = 6;

update tbl_board set title='test01', content='test01', writer='test01' where bno=1;

insert into tbl_board (title, content, writer) (select title, content, writer from tbl_board);

select * from tbl_board order by bno desc limit 1, 10

select count(bno) from tbl_board;

create table tbl_reply(
	rno int not null auto_increment,
	bno int not null default 0,
	replytext varchar(1000) not null,
	replyer varchar(50) not null,
	regdate timestamp not null default now(),
	updatedate timestamp not null default now(),
	primary key(rno)
);

alter table tbl_reply add constraint fk_board
foreign key(bno) references tbl_board(bno) on delete cascade;

select * from book_ex.tbl_reply;

insert into tbl_reply (bno, replytext, replyer) values(1798, '보미가 남김', '보미넴');

create table tbl_user(
	uid varchar(50) not null,
	upw varchar(50) not null,
	uname varchar(50) not null,
	upoint int not null default 0,
	primary key(uid)
);

create table tbl_message(
	mid int not null auto_increment,
	targetid varchar(50) not null,
	sender varchar(50) not null,
	message text not null,
	opendate timestamp,
	senddate timestamp not null default now(),
	primary key(mid)
);

-- 외래키 조약때문에 create 할 때 이미 존재하는 회원으로 등록해야함
alter table tbl_message add constraint fk_usertarget
foreign key (targetid) references tbl_user(uid);

alter table tbl_message add constraint fk_usersender
foreign key (sender) references tbl_user(uid);

insert into tbl_user(uid, upw, uname) values('user00', 'user00', 'iron man');
insert into tbl_user(uid, upw, uname) values('user01', 'user01', 'captain');
insert into tbl_user(uid, upw, uname) values('user02', 'user02', 'hulk');
insert into tbl_user(uid, upw, uname) values('user03', 'user03', 'thor');
insert into tbl_user(uid, upw, uname) values('user04', 'user04', 'quick silver');

select * from tbl_user;
select * from tbl_message;

use book_ex;

-- 댓글 카운트 증가 시키기
alter table tbl_board add column replycnt int default 0;
update tbl_board set replycnt = (select count(rno) from tbl_reply where bno=tbl_board.bno);

select * from tbl_board where bno=804;

create table tbl_attach(
	fullname varchar(150) not null,
	bno int not null,
	regdate timestamp default now(),
	primary key(fullname)
)

select * from tbl_attach;

alter table tbl_attach add constraint fk_board_attach
foreign key (bno) references tbl_board(bno);

insert into tbl_attach values('abc', 1798, now());

delete from tbl_attach where fullname='abc' and bno=1798;