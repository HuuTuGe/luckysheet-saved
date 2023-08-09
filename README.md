    官方Dome虽然好，但入门门槛较高,本项目尽量精简,带大家快速体验

# 功能

- 自定义昵称
- 实时协同编辑
- 自动保存

## 根文件夹要是lucksheet-saved-in-recovery-master
## 演示地址(服务器带宽有限,加载缓慢)
```dtd
http://115.159.27.224:11551/
```
## 本项目主要采用java(springboot)编辑,使用maven构建项目
## 直接运行 Application 启动程序

## 快速入门
### 服务器
- LuckySheetController 用于文档的整存整取
- LuckySheetWebSocketServer 提供websocket链接,用于同步文档的实时修改状态
### 前端位于resources/static中
- index.html 用于配置获取/保存/同步文档的地址

## 分支说明:
### dev-2.1.13 新版支持
- 为新版提供支持
### dev-1-邂逅 实现了一个最简单的协同编辑
- 可以同步所有操作到所有客户端
### dev-2-相识
- 自定义昵称
- 实时协同编辑
- 自动保存