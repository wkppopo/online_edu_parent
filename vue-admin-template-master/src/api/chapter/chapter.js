import chapter from "@/api/chapter/chapter_back";
import section from "@/api/chapter/section_back";

export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      //在created中获取全局courseId
      courseId: "",
      //章节小节信息集合
      chapterAndSectionList: [],
      //dialog对象包含的信息
      chapterUI: {
        title: "",
        sort: 0,
        //章节中使用的全局courseID
        courseId: "",
      },
      //dialog是否显示
      chapterFormVisible: false,
      //章节按钮是否可用
      saveChapterBtnDisabled: false,
      //--------------------------------------
      //表单是否可见
      sectionFormVisible: false,
      //小节输入信息
      sectionUI: {
        title: '',
        sort: 0,
        isFree: 0,
        courseId: '',
        chapterId:'',
        //小节中包含 视频id
        videoSourceId: '',
        //视频的原始名字
        videoOriginalName: ''
      },
      //小节按钮是否禁用
      saveSectionBtnDisabled: false,
       //视频文件列表
       fileList:[]
    }
  },
  created() {
    //章节页面打开时，将地址栏的id给 chapterUI.courseId
    this.chapterUI.courseId = this.$route.params.courseId;
    //页面打开的时候，获取所有的章节小节
    this.getChapterAndSection();
  },

  methods: {
     //--------------------------上传视频
  //1.成功上传
  uploadVideoSuccess(response, file) {
    //上传成功之后需要把视频id和视频名称存储在数据库中
    this.sectionUI.videoSourceId=response.data.videoId;
    this.sectionUI.videoOriginalName=file.name;
  },
  //2.删除视频之前
  beforeVideoDelete(file, fileList) {
    return this.$confirm(`确定要删除${file.name}`);
  },
  //3.执行视频删除
  deleteVideo(file, fileList) {

    //a.删除阿里云上面得视频
    section.deleteSingleVideo(this.sectionUI.videoSourceId)
    .then(response=>{
      //删除成功之后弹框
      this.$message({
        type: "success",
        message: "删除成功"
      });

      //b.删除数据库里面的数据
      this.sectionUI.videoSourceId='';
      this.sectionUI.videoOriginalName='';
      //把内存里面的文件列表置为空
      this.fileList=[]
    })
  },
  //4.视图上传多于一个视频
  uploadExceed(files, fileList) {
    console.log(files);
  },
  //---------------------------小节----------------------------
    //2.添加小节弹框
    saveSectionDialog(chapterId) {
      this.sectionFormVisible = true;
      this.sectionUI = {};
      //因为上面的清空了所有内容 所以需要单独赋值
      this.sectionUI.courseId = this.$route.params.courseId;
      this.sectionUI.chapterId = chapterId;
    },
    //2.添加小节
    saveSection() {
      section.saveSection(this.sectionUI)
        .then(response => {
          //添加成功弹框
          this.$message({
            message: "添加成功",
            type: "success"
          });
          //关闭输入弹框
          this.sectionFormVisible = false;
          this.getChapterAndSection();
        })
    },
    //3.修改小节弹框
    updateSectionDialog(sectionId) {
      this.sectionFormVisible = true;
      section.getSectionById(sectionId)
        .then(response => {
          //查到小节对象用来回显
          this.sectionUI = response.data.section;
          //先清空文件列表里面的内容
          this.fileList=[];
          //如果视频名称不为空
          if(!this.sectionUI.videoOriginalName==""){
            this.fileList=[{'name':this.sectionUI.videoOriginalName}]
          }
        })
    },

    //3.修改小节
    updateSection() {
      section.updateSection(this.sectionUI)
        .then(response => {
          //修改成功弹框
          this.$message({
            message: "修改成功",
            type: "success"
          });
          //关闭输入弹框
          this.sectionFormVisible = false;
          this.getChapterAndSection();
        })
    },
    //4.添加或修改小节
    saveOrUpdateSection() {
      //如果有小节id代表是修改
      if (this.sectionUI.id) {
        this.updateSection();
      } else {
        this.saveSection();
      }
    },
    //5.删除小节
    deleteSection(sectionId) {
      this.$confirm("确定下此狠手?", "提示", {
        confirmButtonText: "确定干",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          section.deleteSection(sectionId).then(response => {
            this.$message({
              message: "删除成功",
              type: "success"
            });
            this.getChapterAndSection();
          })
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消删除"
          });
        });
    },
    //--------------------------------------
    //5.删除章节
    deleteChapter(chapterId) {
      this.$confirm("确定下此狠手?", "提示", {
        confirmButtonText: "确定干",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          chapter.deleteChapter(chapterId).then(response => {
            this.$message({
              message: "删除成功",
              type: "success"
            });
            this.getChapterAndSection();
          })
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消删除"
          });
        });
    },
    //3.修改章节
    updateChapter() {
      chapter.updateChapter(this.chapterUI)
        .then(response => {
          //修改成功弹框
          this.$message({
            message: "修改成功",
            type: "success"
          });
          //关闭输入弹框
          this.chapterFormVisible = false;
          this.getChapterAndSection();
        })
    },
    //4.添加或修改章节
    saveOrUpdateChapter() {
      //如果有章节id代表是修改,这个id从页面章节的修改按钮来
      if (this.chapterUI.id) {
        this.updateChapter();
      } else {
        this.saveChapter();
      }
    },
    //修改章节信息回显
    updateChapterDialog(chapterId) {
      this.chapterFormVisible = true;
      chapter.getChapterById(chapterId)
        .then(response => {
          this.chapterUI = response.data.chapter;
        })
    },
    //向后端发起保存章节信息
    saveChapter() {
      chapter.saveChapter(this.chapterUI).then(res => {
        this.$message({
          message: "添加成功",
          type: "success"
        });
        //关闭弹窗
        this.chapterFormVisible = false;
        this.getChapterAndSection();
      })
    },
    //添加章节 点击弹框显示
    saveChapterDialog() {
      this.chapterFormVisible = true;
      this.chapterUI = {};
      //因为上面的清空了所有内容 所以需要单独赋值
      this.chapterUI.courseId = this.$route.params.courseId;
    },
    //获取所有的章节和小节 
    getChapterAndSection() {
      chapter.getChapterAndSection(this.chapterUI.courseId).then(res => {
        this.chapterAndSectionList = res.data.chapterAndSectionList;
      });
    },
    previous() {
      console.log('previous')
      this.$router.push({ path: '/course/form/' + this.chapterUI.courseId })
    },

    next() {
      console.log('next')
      this.$router.push({ path: '/course/publish/' + this.chapterUI.courseId })
    },

  }
}