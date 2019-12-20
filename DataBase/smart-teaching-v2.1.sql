create table user
(
  id               int auto_increment
  comment '用户唯一ID'
    primary key,
  user_name        varchar(20) null
  comment '用户名字',
  user_gender      tinyint(2)  null
  comment '用户性别',
  user_unique_id   varchar(20) null
  comment '学号（工号）',
  user_mail        varchar(20) null
  comment '用户邮箱',
  user_phone       varchar(20) null
  comment '用户手机号',
  account_name     varchar(20) not null
  comment '账户名',
  account_password varchar(20) not null
  comment '账户密码',
  account_type     int         not null
  comment '账户类型（0：学生，1：教师）',
  account_status   int         null
  comment '账户状态（登录、冻结、删除）'
);

create table classes
(
  id                int auto_increment
  comment '班级唯一ID'
    primary key,
  class_status      int                                 null
  comment '班级状态',
  class_name        varchar(20)                         null
  comment '班级名称',
  class_type        varchar(20)                         null
  comment '班级类型',
  class_create_time timestamp default CURRENT_TIMESTAMP not null
  on update CURRENT_TIMESTAMP
  comment '班级创建时间',
  class_invite_code varchar(255)                        not null
  comment '班级邀请码',
  teacher_id        int                                 not null
  comment '教师ID',
  constraint teacher_id
  foreign key (teacher_id) references user (id)
);

create table class_student
(
  id         int auto_increment
  comment '班级中学生唯一ID'
    primary key,
  class_id   int      not null
  comment '班级ID',
  student_id int      not null
  comment '学生ID',
  join_time  datetime not null
  comment '加入时间',
  constraint class_id_class_student
  foreign key (class_id) references classes (id),
  constraint student_id_class_student
  foreign key (student_id) references user (id)
);

create table file
(
  id                  int auto_increment
  comment '文件唯一ID'
    primary key,
  file_name           varchar(40)  not null
  comment '文件名',
  file_extend_name    varchar(40)  not null
  comment '文件扩展名',
  file_status         int          null
  comment '文件状态',
  author_id           int          not null
  comment '文件上传者ID',
  file_save_path      varchar(255) not null
  comment '文件保存路径',
  file_tags           varchar(255) null
  comment '文件标签',
  file_download_times int          null
  comment '文件下载次数',
  file_timeout        datetime     null
  comment '文件失效时间',
  file_type           int          not null
  comment '文件类型（0：资源，1：作业，2：报告）',
  file_upload_time    datetime     null
  comment '文件上传时间
',
  constraint author_id
  foreign key (author_id) references user (id)
);

create index file_id
  on file (id);

create table homework
(
  id              int auto_increment
  comment '作业唯一ID'
    primary key,
  homework_format varchar(255) not null
  comment '作业支持上传的文件格式',
  file_id         int          not null
  comment '文件id',
  class_id        int          not null
  comment '可见班级ID',
  constraint class_id
  foreign key (class_id) references classes (id),
  constraint file_id
  foreign key (file_id) references file (id)
);

create table question_bank
(
  id            int auto_increment
  comment '题库唯一ID'
    primary key,
  status        varchar(255) null
  comment '题库状态',
  question_name varchar(255) not null
  comment '题库名称',
  teacher_id    int(255)     not null
  comment '创建的教师',
  create_time   datetime     not null
  comment '创建时间',
  tags          varchar(0)   null
  comment '题库标签',
  constraint teacher_id_quesion_bank
  foreign key (teacher_id) references user (id)
);

create table question
(
  id               int auto_increment
  comment '题目（问题）唯一ID'
    primary key,
  status           varchar(255) not null
  comment '题目状态',
  question_bank_id int          null
  comment '所属题库ID',
  create_time      datetime     not null
  comment '创建时间',
  question_type    tinyint(255) not null
  comment '问题类型',
  tags             varchar(255) null
  comment '问题标签',
  description      varchar(255) not null
  comment '问题描述',
  options          varchar(255) null
  comment '问题选项',
  answer           varchar(255) null
  comment '问题答案',
  analysis         varchar(255) null
  comment '问题解析',
  constraint quesion_bank_id_quesion
  foreign key (question_bank_id) references question_bank (id)
);

create table report
(
  id                int auto_increment
  comment '报告唯一ID'
    primary key,
  report_score      int(255)     null
  comment '报告得分',
  report_evaluation varchar(255) null
  comment '报告评价',
  report_format     varchar(255) not null
  comment '报告支持的文件格式',
  file_id           int          not null
  comment '对应的文件ID',
  homework_id       int          not null
  comment '对应作业ID',
  constraint file_id_report
  foreign key (file_id) references file (id),
  constraint homework_id_report
  foreign key (homework_id) references homework (id)
);

create index account_id
  on user (account_name);

