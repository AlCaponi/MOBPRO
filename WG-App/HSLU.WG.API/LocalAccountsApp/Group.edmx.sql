
-- --------------------------------------------------
-- Entity Designer DDL Script for SQL Server 2005, 2008, 2012 and Azure
-- --------------------------------------------------
-- Date Created: 05/10/2015 07:10:58
-- Generated from EDMX file: C:\REPOS\MOBPRO\WG-App\HSLU.WG.API\LocalAccountsApp\Group.edmx
-- --------------------------------------------------

SET QUOTED_IDENTIFIER OFF;
GO
USE [WGApp];
GO
IF SCHEMA_ID(N'dbo') IS NULL EXECUTE(N'CREATE SCHEMA [dbo]');
GO

-- --------------------------------------------------
-- Dropping existing FOREIGN KEY constraints
-- --------------------------------------------------


-- --------------------------------------------------
-- Dropping existing tables
-- --------------------------------------------------

IF OBJECT_ID(N'[ApiUser].[Groups]', 'U') IS NOT NULL
    DROP TABLE [ApiUser].[Groups];
GO

-- --------------------------------------------------
-- Creating all tables
-- --------------------------------------------------

-- Creating table 'Groups'
CREATE TABLE [dbo].[Groups] (
    [GroupID] int  NOT NULL,
    [GroupName] varchar(50)  NULL
);
GO

-- Creating table 'ListEntitySet'
CREATE TABLE [dbo].[ListEntitySet] (
    [ListID] int IDENTITY(1,1) NOT NULL,
    [ListName] nvarchar(max)  NOT NULL,
    [GroupsGroupID] int  NOT NULL
);
GO

-- Creating table 'ListItemEntitySet'
CREATE TABLE [dbo].[ListItemEntitySet] (
    [ListItemID] int IDENTITY(1,1) NOT NULL,
    [Name] nvarchar(max)  NOT NULL,
    [IsChecked] nvarchar(max)  NOT NULL,
    [CreatedDate] nvarchar(max)  NOT NULL,
    [ListEntityListID] int  NOT NULL
);
GO

-- --------------------------------------------------
-- Creating all PRIMARY KEY constraints
-- --------------------------------------------------

-- Creating primary key on [GroupID] in table 'Groups'
ALTER TABLE [dbo].[Groups]
ADD CONSTRAINT [PK_Groups]
    PRIMARY KEY CLUSTERED ([GroupID] ASC);
GO

-- Creating primary key on [ListID] in table 'ListEntitySet'
ALTER TABLE [dbo].[ListEntitySet]
ADD CONSTRAINT [PK_ListEntitySet]
    PRIMARY KEY CLUSTERED ([ListID] ASC);
GO

-- Creating primary key on [ListItemID] in table 'ListItemEntitySet'
ALTER TABLE [dbo].[ListItemEntitySet]
ADD CONSTRAINT [PK_ListItemEntitySet]
    PRIMARY KEY CLUSTERED ([ListItemID] ASC);
GO

-- --------------------------------------------------
-- Creating all FOREIGN KEY constraints
-- --------------------------------------------------

-- Creating foreign key on [GroupsGroupID] in table 'ListEntitySet'
ALTER TABLE [dbo].[ListEntitySet]
ADD CONSTRAINT [FK_GroupsListEntity]
    FOREIGN KEY ([GroupsGroupID])
    REFERENCES [dbo].[Groups]
        ([GroupID])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_GroupsListEntity'
CREATE INDEX [IX_FK_GroupsListEntity]
ON [dbo].[ListEntitySet]
    ([GroupsGroupID]);
GO

-- Creating foreign key on [ListEntityListID] in table 'ListItemEntitySet'
ALTER TABLE [dbo].[ListItemEntitySet]
ADD CONSTRAINT [FK_ListEntityListItemEntity]
    FOREIGN KEY ([ListEntityListID])
    REFERENCES [dbo].[ListEntitySet]
        ([ListID])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_ListEntityListItemEntity'
CREATE INDEX [IX_FK_ListEntityListItemEntity]
ON [dbo].[ListItemEntitySet]
    ([ListEntityListID]);
GO

-- --------------------------------------------------
-- Script has ended
-- --------------------------------------------------