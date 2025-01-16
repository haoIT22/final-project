USE [master]
GO
/****** Object:  Database [rentalManagement]    Script Date: 16/01/2025 6:37:41 CH ******/
CREATE DATABASE [rentalManagement]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'rentalManagement', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\rentalManagement.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'rentalManagement_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\rentalManagement_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [rentalManagement] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [rentalManagement].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [rentalManagement] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [rentalManagement] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [rentalManagement] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [rentalManagement] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [rentalManagement] SET ARITHABORT OFF 
GO
ALTER DATABASE [rentalManagement] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [rentalManagement] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [rentalManagement] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [rentalManagement] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [rentalManagement] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [rentalManagement] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [rentalManagement] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [rentalManagement] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [rentalManagement] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [rentalManagement] SET  DISABLE_BROKER 
GO
ALTER DATABASE [rentalManagement] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [rentalManagement] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [rentalManagement] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [rentalManagement] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [rentalManagement] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [rentalManagement] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [rentalManagement] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [rentalManagement] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [rentalManagement] SET  MULTI_USER 
GO
ALTER DATABASE [rentalManagement] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [rentalManagement] SET DB_CHAINING OFF 
GO
ALTER DATABASE [rentalManagement] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [rentalManagement] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [rentalManagement] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [rentalManagement] SET QUERY_STORE = OFF
GO
USE [rentalManagement]
GO
/****** Object:  Table [dbo].[Rooms]    Script Date: 16/01/2025 6:37:42 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rooms](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Address] [nvarchar](50) NOT NULL,
	[RoomID] [nvarchar](50) NOT NULL,
	[Floor] [int] NOT NULL,
	[Tenants] [nvarchar](50) NOT NULL,
	[Price] [decimal](18, 2) NOT NULL,
	[Status] [nvarchar](50) NOT NULL,
	[Area] [decimal](18, 2) NOT NULL,
	[Elec] [nvarchar](50) NOT NULL,
	[Water] [nvarchar](50) NOT NULL,
	[Wifi] [nvarchar](50) NOT NULL,
	[Garbage] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Rooms] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 16/01/2025 6:37:42 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](50) NOT NULL,
	[PhoneNum] [nvarchar](50) NOT NULL,
	[CreatedAt] [datetime] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
USE [master]
GO
ALTER DATABASE [rentalManagement] SET  READ_WRITE 
GO
